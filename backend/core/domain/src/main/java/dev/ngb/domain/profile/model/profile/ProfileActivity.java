package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Tracks profile activity timestamps for ranking and discovery.
 */
public class ProfileActivity extends DomainEntity<Long> {

    private Long profileId;
    private Instant lastActiveAt;
    private Instant lastPostAt;
}
