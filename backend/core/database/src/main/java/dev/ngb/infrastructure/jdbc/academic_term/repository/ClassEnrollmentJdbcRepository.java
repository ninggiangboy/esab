package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.ClassEnrollment;
import dev.ngb.domain.academic_term.repository.ClassEnrollmentRepository;
import dev.ngb.domain.academic_term.model.EnrollmentStatus;
import dev.ngb.infrastructure.jdbc.academic_term.entity.ClassEnrollmentEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class ClassEnrollmentJdbcRepository
        extends JdbcRepository<ClassEnrollment, ClassEnrollmentEntity, UUID>
        implements ClassEnrollmentRepository {

    public ClassEnrollmentJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ClassEnrollmentEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ClassEnrollment mapToDomain(ClassEnrollmentEntity entity) {
        return ClassEnrollment.reconstruct(
                entity.getId(),
                entity.getAcademyTermId(),
                entity.getClassId(),
                entity.getStudentCode(),
                EnrollmentStatus.valueOf(entity.getStatus()),
                entity.getGrade(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected ClassEnrollmentEntity mapToJdbc(ClassEnrollment domain) {
        return ClassEnrollmentEntity.builder()
                .id(domain.getId())
                .academyTermId(domain.getAcademyTermId())
                .classId(domain.getClassId())
                .studentCode(domain.getStudentCode())
                .status(domain.getStatus().name())
                .grade(domain.getGrade())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
