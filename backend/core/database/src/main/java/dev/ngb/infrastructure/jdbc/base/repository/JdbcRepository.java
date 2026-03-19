package dev.ngb.infrastructure.jdbc.base.repository;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.Repository;
import dev.ngb.infrastructure.jdbc.base.entity.JdbcEntity;
import dev.ngb.infrastructure.jdbc.base.entity.SoftDeletable;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.util.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Sort;
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

    private final JdbcClient jdbcClient;
    protected final JdbcTemplate jdbcTemplate;
    private final JdbcAggregateTemplate jdbcAggregate;
    private final Class<J> clazz;
    private final boolean softDeleteSupported;
    private final JdbcMapper<D, J> mapper;

    protected JdbcRepository(
            Class<J> clazz,
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMapper<D, J> mapper
    ) {
        this.clazz = clazz;
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAggregate = jdbcAggregate;
        this.softDeleteSupported = SoftDeletable.class.isAssignableFrom(clazz);
        this.mapper = Objects.requireNonNull(mapper, "mapper must not be null");
    }

    protected D mapToDomain(J entity) {
        return mapper.toDomain(entity);
    }

    protected J mapToJdbc(D entity) {
        return mapper.toJdbc(entity);
    }

    protected Query buildQuery(@Nullable Criteria criteria) {
        return buildQuery(criteria, null);
    }

    protected Query buildQuery(@Nullable Criteria criteria, @Nullable Sort sort) {
        Query query;

        if (!softDeleteSupported) {
            query = criteria == null ? Query.empty() : Query.query(criteria);
        } else {
            Criteria defaultCriteria = Criteria.where(IS_DELETED_COLUMN).isFalse();
            Criteria finalCriteria = criteria == null
                    ? defaultCriteria
                    : defaultCriteria.and(criteria);
            query = Query.query(finalCriteria);
        }

        if (sort != null && sort.isSorted()) {
            query = query.sort(sort);
        }

        return query;
    }

    protected List<D> findAllBy(@Nullable Criteria criteria) {
        return findAllBySorted(criteria, null);
    }

    protected List<D> findAllBySorted(@Nullable Criteria criteria, @Nullable Sort sort) {
        return jdbcAggregate
                .findAll(buildQuery(criteria, sort), clazz)
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    protected Optional<D> findOneBy(@Nullable Criteria criteria) {
        return jdbcAggregate
                .findOne(buildQuery(criteria), clazz)
                .map(this::mapToDomain);
    }

    protected Optional<D> findOneBySorted(@Nullable Criteria criteria, @Nullable Sort sort) {
        return jdbcAggregate
                .findOne(buildQuery(criteria, sort), clazz)
                .map(this::mapToDomain);
    }

    protected long countBy(@Nullable Criteria criteria) {
        return jdbcAggregate.count(buildQuery(criteria), clazz);
    }

    protected boolean existsBy(@Nullable Criteria criteria) {
        return countBy(criteria) > 0;
    }

    protected List<D> findAllByField(String fieldName, Object value) {
        return findAllByFieldSorted(fieldName, value, null);
    }

    protected List<D> findAllByFieldSorted(String fieldName, Object value, @Nullable Sort sort) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return findAllBySorted(criteria, sort);
    }

    protected Optional<D> findOneByField(String fieldName, Object value) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return findOneBy(criteria);
    }

    protected boolean existsByField(String fieldName, Object value) {
        Criteria criteria = Criteria.where(fieldName).is(value);
        return existsBy(criteria);
    }

    protected List<D> findAllSorted(Sort sort) {
        return findAllBySorted(null, sort);
    }

    protected List<D> findAllBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(StringUtils.toSingleLine(sql))
                .params(params)
                .query(clazz)
                .list()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    protected Optional<D> findOneBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(StringUtils.toSingleLine(sql))
                .params(params)
                .query(clazz)
                .optional()
                .map(this::mapToDomain);
    }

    protected long countBySql(String sql, Map<String, ?> params) {
        return jdbcClient.sql(StringUtils.toSingleLine(sql))
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
        Criteria criteria = Criteria.where(ID_COLUMN).is(id);
        return findOneBy(criteria);
    }

    @Override
    public Optional<D> findByUuid(String uuid) {
        Criteria criteria = Criteria.where(UUID_COLUMN).is(uuid);
        return findOneBy(criteria);
    }

    @Override
    public List<D> findByIds(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        Criteria criteria = Criteria.where(ID_COLUMN).in(ids);
        return findAllBy(criteria);
    }

    @Override
    public List<D> findByUuids(List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return List.of();
        }

        Criteria criteria = Criteria.where(UUID_COLUMN).in(uuids);
        return findAllBy(criteria);
    }

    @Override
    public boolean existsById(ID id) {
        Criteria criteria = Criteria.where(ID_COLUMN).is(id);
        return existsBy(criteria);
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
            List<J> saved = jdbcAggregate.saveAll(
                    entities.stream().map(this::mapToJdbc).toList()
            );

            return saved.stream()
                    .map(this::mapToDomain)
                    .toList();

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
            jdbcAggregate.deleteAllById(
                    entities.stream().map(DomainEntity::getId).toList(),
                    clazz
            );
            return;
        }

        try {
            List<J> jdbcEntities = entities.stream()
                    .map(this::mapToJdbc)
                    .toList();

            Instant now = Instant.now();
            jdbcEntities.forEach(e -> ((SoftDeletable) e).setDeletedAt(now));

            jdbcAggregate.saveAll(jdbcEntities);

        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrentModificationException(
                    "Entities were modified concurrently"
            );
        }
    }
}