package dev.ngb.domain.profile.model.graph;

import dev.ngb.domain.DomainEntity;

/**
 * Muting hides a profile's content without blocking.
 * Unlike block, the muted profile is not aware of being muted.
 */
public class ProfileMute extends DomainEntity<Long> {

    private Long muterProfileId;
    private Long mutedProfileId;
}
