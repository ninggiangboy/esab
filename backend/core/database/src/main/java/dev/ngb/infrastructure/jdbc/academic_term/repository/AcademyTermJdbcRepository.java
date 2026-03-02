package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.AcademyTerm;
import dev.ngb.domain.academic_term.repository.AcademyTermRepository;
import dev.ngb.domain.academic_term.model.AcademyTermStatus;
import dev.ngb.infrastructure.jdbc.academic_term.entity.AcademyTermEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.UUID;

public class AcademyTermJdbcRepository
        extends JdbcRepository<AcademyTerm, AcademyTermEntity, UUID>
        implements AcademyTermRepository {

    public AcademyTermJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AcademyTermEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public boolean existsByCodeAndFacilityId(String code, UUID facilityId) {
        Criteria criteria = Criteria
                .where("code").is(code)
                .and("facility_id").is(facilityId);
        return existsBy(criteria);
    }

    @Override
    protected AcademyTerm mapToDomain(AcademyTermEntity entity) {
        return AcademyTerm.reconstruct(
                entity.getId(),
                entity.getFacilityId(),
                entity.getCode(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getAcademicYear(),
                entity.getSemesterInYear(),
                AcademyTermStatus.valueOf(entity.getStatus()),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected AcademyTermEntity mapToJdbc(AcademyTerm domain) {
        return AcademyTermEntity.builder()
                .id(domain.getId())
                .facilityId(domain.getFacilityId())
                .code(domain.getCode())
                .name(domain.getName())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .academicYear(domain.getAcademicYear())
                .semesterInYear(domain.getSemesterInYear())
                .status(domain.getStatus().name())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
