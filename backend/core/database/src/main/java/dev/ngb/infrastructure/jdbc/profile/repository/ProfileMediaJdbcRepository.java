package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.profile.ProfileMedia;
import dev.ngb.domain.profile.repository.ProfileMediaRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileMediaJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileMediaJdbcRepository
        extends JdbcRepository<ProfileMedia, ProfileMediaJdbcEntity, Long>
        implements ProfileMediaRepository {

    public ProfileMediaJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileMediaJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileMedia mapToDomain(ProfileMediaJdbcEntity entity) {
        return ProfileMedia.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getType(),
                entity.getUrl(),
                entity.getMetadata()
        );
    }

    @Override
    protected ProfileMediaJdbcEntity mapToJdbc(ProfileMedia domain) {
        return ProfileMediaJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .type(domain.getType())
                .url(domain.getUrl())
                .metadata(domain.getMetadata())
                .build();
    }

    @Override
    public List<ProfileMedia> findByProfileId(Long profileId) {
        return findAllByField("profileId", profileId);
    }
}
