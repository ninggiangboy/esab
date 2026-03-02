package dev.ngb.infrastructure.jdbc.institution.repository;

import dev.ngb.domain.institution.model.Facility;
import dev.ngb.domain.institution.repository.FacilityRepository;
import dev.ngb.domain.institution.model.FacilityStatus;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.institution.entity.FacilityEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import org.springframework.data.relational.core.query.Criteria;

import java.util.UUID;

public class FacilityJdbcRepository
        extends JdbcRepository<Facility, FacilityEntity, UUID>
        implements FacilityRepository {

    public FacilityJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(FacilityEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public boolean existsByFacilityCodeAndInstitutionId(String facilityCode, UUID institutionId) {
        Criteria criteria = Criteria
                .where("facility_code").is(facilityCode)
                .and("institution_id").is(institutionId);
        return existsBy(criteria);
    }

    @Override
    protected Facility mapToDomain(FacilityEntity entity) {
        return Facility.reconstruct(
                entity.getId(),
                entity.getInstitutionId(),
                entity.getFacilityCode(),
                entity.getCode(),
                entity.getName(),
                FacilityStatus.valueOf(entity.getStatus()),
                entity.getAddress(),
                entity.getTimezone(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected FacilityEntity mapToJdbc(Facility domain) {
        return FacilityEntity.builder()
                .id(domain.getId())
                .institutionId(domain.getInstitutionId())
                .facilityCode(domain.getFacilityCode())
                .code(domain.getCode())
                .name(domain.getName())
                .status(domain.getStatus().name())
                .address(domain.getAddress())
                .timezone(domain.getTimezone())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
