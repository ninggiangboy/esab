package dev.ngb.infrastructure.jdbc.catalog.repository;

import dev.ngb.domain.catalog.model.CourseMaster;
import dev.ngb.domain.catalog.repository.CourseMasterRepository;
import dev.ngb.domain.catalog.model.CourseMasterStatus;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.catalog.entity.CourseMasterEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.*;

public class CourseMasterJdbcRepository
        extends JdbcRepository<CourseMaster, CourseMasterEntity, UUID>
        implements CourseMasterRepository {

    public CourseMasterJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(CourseMasterEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public boolean existsByCode(String code) {
        return existsBy("code", code);
    }

    @Override
    public Optional<CourseMaster> findByCode(String code) {
        return findOneBy("code", code);
    }

    @Override
    public boolean existsAllByCodes(Collection<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return true;
        }

        Set<String> uniqueCodes = new HashSet<>(codes);

        Criteria criteria = Criteria.where("code").in(uniqueCodes);
        Long count = countBy(criteria);

        return count == uniqueCodes.size();
    }

    @Override
    protected CourseMaster mapToDomain(CourseMasterEntity entity) {
        return CourseMaster.reconstruct(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getCredit(),
                entity.getVersion(),
                entity.getSyllabus(),
                entity.getLearningOutcome(),
                entity.getOldCourseCode(),
                CourseMasterStatus.valueOf(entity.getStatus()),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected CourseMasterEntity mapToJdbc(CourseMaster domain) {
        return CourseMasterEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .credit(domain.getCredit())
                .version(domain.getVersion())
                .syllabus(domain.getSyllabus())
                .learningOutcome(domain.getLearningOutcome())
                .oldCourseCode(domain.getOldCourseCode())
                .status(domain.getStatus().name())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
