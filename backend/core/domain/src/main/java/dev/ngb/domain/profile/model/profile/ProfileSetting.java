package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Per-profile privacy and interaction settings.
 * Separated from profile to keep the core entity lean.
 */
public class ProfileSetting extends DomainEntity<Long> {

    private Long profileId;
    private Boolean allowMentions;
    private Boolean allowMessages;
    private Boolean allowTagging;
    private Boolean showActivityStatus;
}
