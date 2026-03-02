package dev.ngb.infrastructure.jdbc.catalog.repository;

import dev.ngb.domain.catalog.model.CoursePrerequisite;
import dev.ngb.domain.catalog.repository.CoursePrerequisiteRepository;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.catalog.entity.CoursePrerequisiteEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CoursePrerequisiteJdbcRepository
        extends JdbcRepository<CoursePrerequisite, CoursePrerequisiteEntity, UUID>
        implements CoursePrerequisiteRepository {

    public CoursePrerequisiteJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(CoursePrerequisiteEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public List<CoursePrerequisite> findByDescendantCode(String descendantCode) {
        return findAllBy("descendant_code", descendantCode);
    }

    @Override
    public List<CoursePrerequisite> findByDescendantCodeIn(Collection<String> descendantCodes) {
        if (descendantCodes == null || descendantCodes.isEmpty()) {
            return List.of();
        }
        Criteria criteria = Criteria.where("descendant_code").in(descendantCodes);
        return findAllBy(criteria);
    }

    @Override
    public List<CoursePrerequisite> findByAncestorCode(String ancestorCode) {
        return findAllBy("ancestor_code", ancestorCode);
    }

    @Override
    public boolean existsByAncestorCodeAndDescendantCodeIn(String ancestorCode, Collection<String> descendantCodes) {
        if (descendantCodes == null || descendantCodes.isEmpty()) {
            return false;
        }
        Criteria criteria = Criteria.where("ancestor_code").is(ancestorCode)
                .and("descendant_code").in(descendantCodes);
        return existsBy(criteria);
    }

    @Override
    protected CoursePrerequisite mapToDomain(CoursePrerequisiteEntity entity) {
        return CoursePrerequisite.reconstruct(
                entity.getId(),
                entity.getAncestorCode(),
                entity.getDescendantCode(),
                entity.getDepth(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected CoursePrerequisiteEntity mapToJdbc(CoursePrerequisite domain) {
        return CoursePrerequisiteEntity.builder()
                .id(domain.getId())
                .ancestorCode(domain.getAncestorCode())
                .descendantCode(domain.getDescendantCode())
                .depth(domain.getDepth())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
