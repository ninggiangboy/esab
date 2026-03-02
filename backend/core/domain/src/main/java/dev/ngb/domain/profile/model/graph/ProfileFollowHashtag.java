package dev.ngb.domain.profile.model.graph;

import dev.ngb.domain.DomainEntity;

/**
 * Allows a profile to follow a hashtag and receive its feed.
 */
public class ProfileFollowHashtag extends DomainEntity<Long> {

    private Long profileId;
    private Long hashtagId;
}
