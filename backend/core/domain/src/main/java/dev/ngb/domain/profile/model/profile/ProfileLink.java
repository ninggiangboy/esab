package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * External link displayed on a profile (e.g. website, social media).
 */
public class ProfileLink extends DomainEntity<Long> {

    private Long profileId;
    private ProfileLinkType type;
    private String url;
    private Integer orderIndex;
}
