package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Media asset associated with a profile (avatar, banner, or highlight).
 */
public class ProfileMedia extends DomainEntity<Long> {

    private Long profileId;
    private ProfileMediaType type;
    private String url;
    private String metadata;
}
