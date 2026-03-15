package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.activity.ProfileActivity;
import dev.ngb.domain.profile.repository.ProfileActivityRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileActivityJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileActivityJdbcRepository
        extends JdbcRepository<ProfileActivity, ProfileActivityJdbcEntity, Long>
        implements ProfileActivityRepository {

    public ProfileActivityJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileActivityJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileActivity mapToDomain(ProfileActivityJdbcEntity entity) {
        return ProfileActivity.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getLastActiveAt(),
                entity.getLastPostAt()
        );
    }

    @Override
    protected ProfileActivityJdbcEntity mapToJdbc(ProfileActivity domain) {
        return ProfileActivityJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .lastActiveAt(domain.getLastActiveAt())
                .lastPostAt(domain.getLastPostAt())
                .build();
    }

    @Override
    public Optional<ProfileActivity> findByProfileId(Long profileId) {
        return findOneBy("profileId", profileId);
    }
}
