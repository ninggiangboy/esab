package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.profile.Profile;
import dev.ngb.domain.profile.model.profile.ProfileActivity;
import dev.ngb.domain.profile.model.profile.ProfileLink;
import dev.ngb.domain.profile.model.profile.ProfileMedia;
import dev.ngb.domain.profile.model.profile.ProfileMetadata;
import dev.ngb.domain.profile.model.profile.ProfileSetting;
import dev.ngb.domain.profile.model.profile.ProfileStats;
import dev.ngb.domain.profile.model.profile.ProfileUsername;
import dev.ngb.domain.profile.repository.ProfileRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileActivityJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileLinkJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileMediaJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileMetadataJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileSettingJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileStatsJdbcEntity;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileUsernameJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

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
                entity.getIsVerified(),
                entity.getStats() == null ? null : mapStatsToDomain(entity.getStats()),
                entity.getSetting() == null ? null : mapSettingToDomain(entity.getSetting()),
                entity.getActivity() == null ? null : mapActivityToDomain(entity.getActivity()),
                entity.getLinks() == null ? Set.of() : entity.getLinks().stream().map(this::mapLinkToDomain).collect(Collectors.toSet()),
                entity.getMedias() == null ? Set.of() : entity.getMedias().stream().map(this::mapMediaToDomain).collect(Collectors.toSet()),
                entity.getMetadata() == null ? Set.of() : entity.getMetadata().stream().map(this::mapMetadataToDomain).collect(Collectors.toSet()),
                entity.getUsernameHistory() == null ? Set.of() : entity.getUsernameHistory().stream().map(this::mapUsernameToDomain).collect(Collectors.toSet())
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
                .stats(domain.getStats() == null ? null : mapStatsToJdbc(domain.getStats()))
                .setting(domain.getSetting() == null ? null : mapSettingToJdbc(domain.getSetting()))
                .activity(domain.getActivity() == null ? null : mapActivityToJdbc(domain.getActivity()))
                .links(domain.getLinks() == null ? null : domain.getLinks().stream().map(this::mapLinkToJdbc).collect(Collectors.toSet()))
                .medias(domain.getMedias() == null ? null : domain.getMedias().stream().map(this::mapMediaToJdbc).collect(Collectors.toSet()))
                .metadata(domain.getMetadata() == null ? null : domain.getMetadata().stream().map(this::mapMetadataToJdbc).collect(Collectors.toSet()))
                .usernameHistory(domain.getUsernameHistory() == null ? null : domain.getUsernameHistory().stream().map(this::mapUsernameToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private ProfileStats mapStatsToDomain(ProfileStatsJdbcEntity entity) {
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

    private ProfileStatsJdbcEntity mapStatsToJdbc(ProfileStats domain) {
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

    private ProfileSetting mapSettingToDomain(ProfileSettingJdbcEntity entity) {
        return ProfileSetting.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getAllowMentions(),
                entity.getAllowMessages(),
                entity.getAllowTagging(),
                entity.getShowActivityStatus()
        );
    }

    private ProfileSettingJdbcEntity mapSettingToJdbc(ProfileSetting domain) {
        return ProfileSettingJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .allowMentions(domain.getAllowMentions())
                .allowMessages(domain.getAllowMessages())
                .allowTagging(domain.getAllowTagging())
                .showActivityStatus(domain.getShowActivityStatus())
                .build();
    }

    private ProfileActivity mapActivityToDomain(ProfileActivityJdbcEntity entity) {
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

    private ProfileActivityJdbcEntity mapActivityToJdbc(ProfileActivity domain) {
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

    private ProfileLink mapLinkToDomain(ProfileLinkJdbcEntity entity) {
        return ProfileLink.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getType(),
                entity.getUrl(),
                entity.getOrderIndex()
        );
    }

    private ProfileLinkJdbcEntity mapLinkToJdbc(ProfileLink domain) {
        return ProfileLinkJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .type(domain.getType())
                .url(domain.getUrl())
                .orderIndex(domain.getOrderIndex())
                .build();
    }

    private ProfileMedia mapMediaToDomain(ProfileMediaJdbcEntity entity) {
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

    private ProfileMediaJdbcEntity mapMediaToJdbc(ProfileMedia domain) {
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

    private ProfileMetadata mapMetadataToDomain(ProfileMetadataJdbcEntity entity) {
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

    private ProfileMetadataJdbcEntity mapMetadataToJdbc(ProfileMetadata domain) {
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

    private ProfileUsername mapUsernameToDomain(ProfileUsernameJdbcEntity entity) {
        return ProfileUsername.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getUsername(),
                entity.getIsCurrent()
        );
    }

    private ProfileUsernameJdbcEntity mapUsernameToJdbc(ProfileUsername domain) {
        return ProfileUsernameJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .username(domain.getUsername())
                .isCurrent(domain.getIsCurrent())
                .build();
    }
}
