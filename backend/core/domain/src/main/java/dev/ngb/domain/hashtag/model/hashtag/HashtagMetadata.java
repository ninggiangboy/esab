package dev.ngb.domain.hashtag.model.hashtag;

import dev.ngb.domain.DomainEntity;

/**
 * Discovery metadata for a hashtag (category, language, description) used in recommendations.
 */
public class HashtagMetadata extends DomainEntity<Long> {

    private Long hashtagId;
    private String category;
    private String language;
    private String description;
}
