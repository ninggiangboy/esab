package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.stats.ProfileStats;
import dev.ngb.domain.profile.repository.ProfileStatsRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileStatsJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileStatsJdbcRepository extends JdbcRepository<ProfileStats, ProfileStatsJdbcEntity, Long>
        implements ProfileStatsRepository {

    public ProfileStatsJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileStatsJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileStats mapToDomain(ProfileStatsJdbcEntity entity) {
        return ProfileStats.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getFollowerCount(),
                entity.getFollowingCount(),
                entity.getThreadCount(),
                entity.getLikeCount(),
                entity.getMediaCount()
        );
    }

    @Override
    protected ProfileStatsJdbcEntity mapToJdbc(ProfileStats domain) {
        return ProfileStatsJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .followerCount(domain.getFollowerCount())
                .followingCount(domain.getFollowingCount())
                .threadCount(domain.getThreadCount())
                .likeCount(domain.getLikeCount())
                .mediaCount(domain.getMediaCount())
                .build();
    }

    @Override
    public Optional<ProfileStats> findByProfileId(Long profileId) {
        return findOneByField("profile_id", profileId);
    }
}

