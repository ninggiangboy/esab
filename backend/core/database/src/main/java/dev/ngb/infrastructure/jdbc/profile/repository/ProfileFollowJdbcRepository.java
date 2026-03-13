package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.graph.ProfileFollow;
import dev.ngb.domain.profile.repository.ProfileFollowRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileFollowJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileFollowJdbcRepository extends JdbcRepository<ProfileFollow, ProfileFollowJdbcEntity, Long> implements ProfileFollowRepository {

    public ProfileFollowJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileFollowJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileFollow mapToDomain(ProfileFollowJdbcEntity entity) {
        return ProfileFollow.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getFollowerProfileId(),
                entity.getFollowingProfileId()
        );
    }

    @Override
    protected ProfileFollowJdbcEntity mapToJdbc(ProfileFollow domain) {
        return ProfileFollowJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .followerProfileId(domain.getFollowerProfileId())
                .followingProfileId(domain.getFollowingProfileId())
                .build();
    }
}
