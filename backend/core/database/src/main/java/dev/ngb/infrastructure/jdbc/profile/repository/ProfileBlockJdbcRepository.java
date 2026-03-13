package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.graph.ProfileBlock;
import dev.ngb.domain.profile.repository.ProfileBlockRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileBlockJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileBlockJdbcRepository extends JdbcRepository<ProfileBlock, ProfileBlockJdbcEntity, Long> implements ProfileBlockRepository {

    public ProfileBlockJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileBlockJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileBlock mapToDomain(ProfileBlockJdbcEntity entity) {
        return ProfileBlock.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getBlockerProfileId(),
                entity.getBlockedProfileId()
        );
    }

    @Override
    protected ProfileBlockJdbcEntity mapToJdbc(ProfileBlock domain) {
        return ProfileBlockJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .blockerProfileId(domain.getBlockerProfileId())
                .blockedProfileId(domain.getBlockedProfileId())
                .build();
    }
}
