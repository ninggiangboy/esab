package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Denormalized counters for a profile, updated asynchronously.
 */
public class ProfileStats extends DomainEntity<Long> {

    private Long profileId;
    private Long followerCount;
    private Long followingCount;
    private Long threadCount;
    private Long likeCount;
    private Long mediaCount;
}
