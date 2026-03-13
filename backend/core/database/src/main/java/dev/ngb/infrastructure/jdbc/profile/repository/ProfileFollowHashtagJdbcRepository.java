package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.graph.ProfileFollowHashtag;
import dev.ngb.domain.profile.repository.ProfileFollowHashtagRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileFollowHashtagJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileFollowHashtagJdbcRepository extends JdbcRepository<ProfileFollowHashtag, ProfileFollowHashtagJdbcEntity, Long> implements ProfileFollowHashtagRepository {

    public ProfileFollowHashtagJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileFollowHashtagJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileFollowHashtag mapToDomain(ProfileFollowHashtagJdbcEntity entity) {
        return ProfileFollowHashtag.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getHashtagId()
        );
    }

    @Override
    protected ProfileFollowHashtagJdbcEntity mapToJdbc(ProfileFollowHashtag domain) {
        return ProfileFollowHashtagJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .hashtagId(domain.getHashtagId())
                .build();
    }
}
