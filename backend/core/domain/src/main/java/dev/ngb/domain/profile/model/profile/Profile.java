package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

import java.util.List;

/**
 * Public persona of a user, separated from the authentication account.
 * <p>
 * Aggregate root of the profile domain. Owns stats, settings, activity, links, media, metadata,
 * and username history. Social graph entities (follow, block, mute) are cross-aggregate relationships.
 */
public class Profile extends DomainEntity<Long> {

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

    private List<ProfileLink> links;
    private List<ProfileMedia> medias;
    private List<ProfileMetadata> metadata;
    private List<ProfileUsername> usernameHistory;
}
