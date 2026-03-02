package dev.ngb.domain.profile.model.graph;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile blocking another profile, preventing all interaction.
 */
public class ProfileBlock extends DomainEntity<Long> {

    private Long blockerProfileId;
    private Long blockedProfileId;
}
