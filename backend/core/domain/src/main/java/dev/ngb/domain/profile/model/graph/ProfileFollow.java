package dev.ngb.domain.profile.model.graph;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a follow relationship between two profiles.
 */
public class ProfileFollow extends DomainEntity<Long> {

    private Long followerProfileId;
    private Long followingProfileId;
}
