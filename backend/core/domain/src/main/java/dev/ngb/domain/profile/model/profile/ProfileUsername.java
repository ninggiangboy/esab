package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Tracks username history for a profile.
 * Enables redirecting old usernames and auditing changes.
 */
public class ProfileUsername extends DomainEntity<Long> {

    private Long profileId;
    private String username;
    private Boolean isCurrent;
}
