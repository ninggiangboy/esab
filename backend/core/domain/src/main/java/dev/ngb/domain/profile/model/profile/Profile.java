package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

/**
 * Public persona of a user, separated from the authentication account.
 * <p>
 * Aggregate root of the profile domain. Owns stats, settings, activity, links, media, metadata,
 * and username history. Social graph entities (follow, block, mute) are cross-aggregate relationships.
 */
@Getter
public class Profile extends DomainEntity<Long> {

    private Profile() {}

    private Long accountId;

    private String username;
    private String displayName;

    private String bio;
    private String website;
    private String location;

    private String avatarUrl;
    private String bannerUrl;

    private ProfileVisibility visibility;
    private Boolean isVerified;

    private ProfileStats stats;
    private ProfileSetting setting;
    private ProfileActivity activity;

    private Set<ProfileLink> links;
    private Set<ProfileMedia> medias;
    private Set<ProfileMetadata> metadata;
    private Set<ProfileUsername> usernameHistory;

    public static Profile reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            Long accountId, String username, String displayName, String bio, String website, String location,
            String avatarUrl, String bannerUrl, ProfileVisibility visibility, Boolean isVerified,
            ProfileStats stats, ProfileSetting setting, ProfileActivity activity,
            Set<ProfileLink> links, Set<ProfileMedia> medias, Set<ProfileMetadata> metadata,
            Set<ProfileUsername> usernameHistory) {
        Profile obj = new Profile();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.accountId = accountId;
        obj.username = username;
        obj.displayName = displayName;
        obj.bio = bio;
        obj.website = website;
        obj.location = location;
        obj.avatarUrl = avatarUrl;
        obj.bannerUrl = bannerUrl;
        obj.visibility = visibility;
        obj.isVerified = isVerified;
        obj.stats = stats;
        obj.setting = setting;
        obj.activity = activity;
        obj.links = links;
        obj.medias = medias;
        obj.metadata = metadata;
        obj.usernameHistory = usernameHistory;
        return obj;
    }
}
