package dev.ngb.infrastructure.jdbc.institution.repository;

import dev.ngb.domain.institution.model.Institution;
import dev.ngb.domain.institution.repository.InstitutionRepository;
import dev.ngb.domain.institution.model.InstitutionStatus;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.institution.entity.InstitutionEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class InstitutionJdbcRepository
        extends JdbcRepository<Institution, InstitutionEntity, UUID>
        implements InstitutionRepository {

    public InstitutionJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(InstitutionEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public boolean existsByCode(String code) {
        return existsBy("code", code);
    }

    @Override
    protected Institution mapToDomain(InstitutionEntity entity) {
        return Institution.reconstruct(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                InstitutionStatus.valueOf(entity.getStatus()),
                entity.getTimezone(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected InstitutionEntity mapToJdbc(Institution domain) {
        return InstitutionEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .status(domain.getStatus().name())
                .timezone(domain.getTimezone())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
