package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Extensible key-value metadata for a profile.
 * e.g. creator_category, pronouns, theme_color.
 */
public class ProfileMetadata extends DomainEntity<Long> {

    private Long profileId;
    private String key;
    private String value;
}
