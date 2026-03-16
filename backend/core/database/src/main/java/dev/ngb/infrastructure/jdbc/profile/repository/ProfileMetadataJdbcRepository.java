package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.profile.ProfileMetadata;
import dev.ngb.domain.profile.repository.ProfileMetadataRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileMetadataJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileMetadataJdbcRepository
        extends JdbcRepository<ProfileMetadata, ProfileMetadataJdbcEntity, Long>
        implements ProfileMetadataRepository {

    public ProfileMetadataJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileMetadataJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileMetadata mapToDomain(ProfileMetadataJdbcEntity entity) {
        return ProfileMetadata.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getKey(),
                entity.getValue()
        );
    }

    @Override
    protected ProfileMetadataJdbcEntity mapToJdbc(ProfileMetadata domain) {
        return ProfileMetadataJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .key(domain.getKey())
                .value(domain.getValue())
                .build();
    }

    @Override
    public List<ProfileMetadata> findByProfileId(Long profileId) {
        return findAllByField("profileId", profileId);
    }
}
