package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.CourseOffer;
import dev.ngb.domain.academic_term.repository.CourseOfferRepository;
import dev.ngb.infrastructure.jdbc.academic_term.entity.CourseOfferEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.UUID;

public class CourseOfferJdbcRepository
        extends JdbcRepository<CourseOffer, CourseOfferEntity, UUID>
        implements CourseOfferRepository {

    public CourseOfferJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(CourseOfferEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public List<CourseOffer> findByAcademyTermId(UUID academyTermId) {
        return findAllBy("academy_term_id", academyTermId);
    }

    @Override
    protected CourseOffer mapToDomain(CourseOfferEntity entity) {
        return CourseOffer.reconstruct(
                entity.getId(),
                entity.getFacilityId(),
                entity.getAcademyTermId(),
                entity.getCourseMasterId(),
                entity.getCode(),
                entity.getMaxStudents(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected CourseOfferEntity mapToJdbc(CourseOffer domain) {
        return CourseOfferEntity.builder()
                .id(domain.getId())
                .facilityId(domain.getFacilityId())
                .academyTermId(domain.getAcademyTermId())
                .courseMasterId(domain.getCourseMasterId())
                .code(domain.getCode())
                .maxStudents(domain.getMaxStudents())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
