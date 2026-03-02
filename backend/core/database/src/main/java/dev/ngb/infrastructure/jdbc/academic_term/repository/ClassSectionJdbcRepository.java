package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.ClassSection;
import dev.ngb.domain.academic_term.repository.ClassSectionRepository;
import dev.ngb.infrastructure.jdbc.academic_term.entity.ClassSectionEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class ClassSectionJdbcRepository
        extends JdbcRepository<ClassSection, ClassSectionEntity, UUID>
        implements ClassSectionRepository {

    public ClassSectionJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ClassSectionEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ClassSection mapToDomain(ClassSectionEntity entity) {
        return ClassSection.reconstruct(
                entity.getId(),
                entity.getFacilityId(),
                entity.getAcademyTermId(),
                entity.getCourseOfferId(),
                entity.getTeacherCode(),
                entity.getCode(),
                entity.getName(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected ClassSectionEntity mapToJdbc(ClassSection domain) {
        return ClassSectionEntity.builder()
                .id(domain.getId())
                .facilityId(domain.getFacilityId())
                .academyTermId(domain.getAcademyTermId())
                .courseOfferId(domain.getCourseOfferId())
                .teacherCode(domain.getTeacherCode())
                .code(domain.getCode())
                .name(domain.getName())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
