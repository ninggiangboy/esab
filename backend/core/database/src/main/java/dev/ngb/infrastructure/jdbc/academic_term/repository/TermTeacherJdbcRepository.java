package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.TermTeacher;
import dev.ngb.domain.academic_term.repository.TermTeacherRepository;
import dev.ngb.domain.academic_term.model.TermTeacherStatus;
import dev.ngb.infrastructure.jdbc.academic_term.entity.TermTeacherEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class TermTeacherJdbcRepository
        extends JdbcRepository<TermTeacher, TermTeacherEntity, UUID>
        implements TermTeacherRepository {

    public TermTeacherJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(TermTeacherEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected TermTeacher mapToDomain(TermTeacherEntity entity) {
        return TermTeacher.reconstruct(
                entity.getId(),
                entity.getFacilityId(),
                entity.getAcademyTermId(),
                entity.getTeacherCode(),
                entity.getFullName(),
                entity.getEmail(),
                TermTeacherStatus.valueOf(entity.getStatus()),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected TermTeacherEntity mapToJdbc(TermTeacher domain) {
        return TermTeacherEntity.builder()
                .id(domain.getId())
                .facilityId(domain.getFacilityId())
                .academyTermId(domain.getAcademyTermId())
                .teacherCode(domain.getTeacherCode())
                .fullName(domain.getFullName())
                .email(domain.getEmail())
                .status(domain.getStatus().name())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
