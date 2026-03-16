package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.profile.Profile;
import dev.ngb.domain.profile.repository.ProfileRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileJdbcRepository extends JdbcRepository<Profile, ProfileJdbcEntity, Long> implements ProfileRepository {

    public ProfileJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected Profile mapToDomain(ProfileJdbcEntity entity) {
        return Profile.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getUsername(),
                entity.getDisplayName(),
                entity.getBio(),
                entity.getWebsite(),
                entity.getLocation(),
                entity.getAvatarUrl(),
                entity.getBannerUrl(),
                entity.getVisibility(),
                entity.getIsVerified()
        );
    }

    @Override
    protected ProfileJdbcEntity mapToJdbc(Profile domain) {
        return ProfileJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .username(domain.getUsername())
                .displayName(domain.getDisplayName())
                .bio(domain.getBio())
                .website(domain.getWebsite())
                .location(domain.getLocation())
                .avatarUrl(domain.getAvatarUrl())
                .bannerUrl(domain.getBannerUrl())
                .visibility(domain.getVisibility())
                .isVerified(domain.getIsVerified())
                .build();
    }

    @Override
    public boolean existsByAccountId(Long accountId) {
        return existsByField("accountId", accountId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return existsByField("username", username);
    }
}
