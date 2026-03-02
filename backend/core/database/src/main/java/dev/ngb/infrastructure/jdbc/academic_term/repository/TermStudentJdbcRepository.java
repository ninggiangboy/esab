package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.TermStudent;
import dev.ngb.domain.academic_term.repository.TermStudentRepository;
import dev.ngb.domain.academic_term.model.TermStudentStatus;
import dev.ngb.infrastructure.jdbc.academic_term.entity.TermStudentEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class TermStudentJdbcRepository
        extends JdbcRepository<TermStudent, TermStudentEntity, UUID>
        implements TermStudentRepository {

    public TermStudentJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(TermStudentEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected TermStudent mapToDomain(TermStudentEntity entity) {
        return TermStudent.reconstruct(
                entity.getId(),
                entity.getFacilityId(),
                entity.getAcademyTermId(),
                entity.getStudentCode(),
                entity.getFullName(),
                entity.getDob(),
                entity.getEmail(),
                TermStudentStatus.valueOf(entity.getStatus()),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected TermStudentEntity mapToJdbc(TermStudent domain) {
        return TermStudentEntity.builder()
                .id(domain.getId())
                .facilityId(domain.getFacilityId())
                .academyTermId(domain.getAcademyTermId())
                .studentCode(domain.getStudentCode())
                .fullName(domain.getFullName())
                .dob(domain.getDob())
                .email(domain.getEmail())
                .status(domain.getStatus().name())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
