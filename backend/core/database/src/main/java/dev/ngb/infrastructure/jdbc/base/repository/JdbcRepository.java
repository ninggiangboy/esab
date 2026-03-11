package dev.ngb.infrastructure.jdbc.base.repository;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.Repository;
import dev.ngb.infrastructure.jdbc.base.entity.JdbcEntity;
import dev.ngb.infrastructure.jdbc.base.entity.SoftDeletable;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Transactional
public abstract class JdbcRepository<D extends DomainEntity<ID>, J extends JdbcEntity<ID>, ID>
        implements Repository<D, ID> {

    protected final String ID_COLUMN = "id";
    protected final String UUID_COLUMN = "uuid";
    protected final String IS_DELETED_COLUMN = "is_deleted";

    protected final JdbcClient jdbcClient;
    protected final JdbcTemplate jdbcTemplate;
    protected final JdbcAggregateTemplate jdbcAggregate;
    protected final JdbcMetadataHelper jdbcMetadataHelper;
    protected final Class<J> clazz;
    protected final boolean softDeleteSupported;

    protected JdbcRepository(
            Class<J> clazz,
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        this.clazz = clazz;
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAggregate = jdbcAggregate;
        this.jdbcMetadataHelper = jdbcMetadataHelper;
        this.softDeleteSupported = SoftDeletable.class.isAssignableFrom(clazz);
    }

    protected abstract D mapToDomain(J entity);

    protected abstract J mapToJdbc(D entity);

    protected Query fromCriteria(@Nullable Criteria criteria) {
        if (!softDeleteSupported) {
            return criteria == null ? Query.empty() : Query.query(criteria);
        }
        Criteria defaultCriteria = Criteria.where(IS_DELETED_COLUMN).isFalse();

        Criteria finalCriteria = criteria == null
                ? defaultCriteria
                : defaultCriteria.and(criteria);

        return Query.query(finalCriteria);
    }

    protected List<D> findAllBy(@Nullable Criteria criteria) {
        return jdbcAggregate.findAll(fromCriteria(criteria), clazz).stream().map(this::mapToDomain).toList();
    }

    protected Optional<D> findOneBy(@Nullable Criteria criteria) {
        return jdbcAggregate.findOne(fromCriteria(criteria), clazz).map(this::mapToDomain);
    }

    protected Long countBy(@Nullable Criteria criteria) {
        return jdbcAggregate.count(fromCriteria(criteria), clazz);
    }

    protected boolean existsBy(@Nullable Criteria criteria) {
        return countBy(criteria) > 0;
    }

    protected List<D> findAllBy(String fieldName, Object value) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return findAllBy(criteria);
    }

    protected Optional<D> findOneBy(String fieldName, Object value) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return findOneBy(criteria);
    }

    protected boolean existsBy(String fieldName, Object value) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return countBy(criteria) > 0;
    }

    protected List<D> findAllBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(sql)
                .params(params)
                .query(clazz)
                .list()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    protected Optional<D> findOneBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(sql)
                .params(params)
                .query(clazz)
                .optional()
                .map(this::mapToDomain);
    }

    protected Long countBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(sql)
                .params(params)
                .query(Long.class)
                .single();
    }

    protected boolean existsBySql(String sql, Map<String, ?> params) {
        return countBySql(sql, params) > 0;
    }

    @Override
    public List<D> findAll() {
        return findAllBy(null);
    }

    @Override
    public Optional<D> findById(ID id) {
        Criteria findByIdCriteria = Criteria.where(ID_COLUMN).is(id);
        return findOneBy(findByIdCriteria);
    }

    @Override
    public Optional<D> findByUuid(String uuid) {
        Criteria findByUuidCriteria = Criteria.where(UUID_COLUMN).is(uuid);
        return findOneBy(findByUuidCriteria);
    }

    @Override
    public List<D> findByIds(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        Criteria findByIdsCriteria = Criteria.where(ID_COLUMN).in(ids);
        return findAllBy(findByIdsCriteria);
    }

    @Override
    public List<D> findByUuids(List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return List.of();
        }
        Criteria findByUuidsCriteria = Criteria.where(UUID_COLUMN).in(uuids);
        return findAllBy(findByUuidsCriteria);
    }

    @Override
    public boolean existsById(ID id) {
        Criteria findByIdCriteria = Criteria.where(ID_COLUMN).is(id);
        return countBy(findByIdCriteria) > 0;
    }

    @Override
    public D save(D entity) {
        try {
            J saved = jdbcAggregate.save(mapToJdbc(entity));
            return mapToDomain(saved);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(
                    "Entity " + entity.getId() + " was modified concurrently"
            );
        }
    }

    @Override
    public List<D> saveAll(List<D> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        try {
            List<J> saved = jdbcAggregate.saveAll(entities.stream().map(this::mapToJdbc).toList());
            return saved.stream().map(this::mapToDomain).toList();
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(
                    "Entities were modified concurrently"
            );
        }
    }

    @Override
    public void delete(D entity) {
        if (!softDeleteSupported) {
            jdbcAggregate.deleteById(entity.getId(), clazz);
            return;
        }
        try {
            J jdbcEntity = mapToJdbc(entity);
            ((SoftDeletable) jdbcEntity).setDeletedAt(Instant.now());
            jdbcAggregate.save(jdbcEntity);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(
                    "Entity " + entity.getId() + " was modified concurrently"
            );
        }
    }

    @Override
    public void deleteAll(List<D> entities) {
        if (entities == null || entities.isEmpty()) {
            return;
        }
        if (!softDeleteSupported) {
            jdbcAggregate.deleteAllById(entities.stream().map(DomainEntity::getId).toList(), clazz);
            return;
        }
        try {
            List<J> jdbcEntity = entities.stream().map(this::mapToJdbc).toList();
            jdbcEntity.forEach(j -> ((SoftDeletable) j).setDeletedAt(Instant.now()));
            jdbcAggregate.saveAll(jdbcEntity);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(
                    "Entities were modified concurrently"
            );
        }
    }
}
