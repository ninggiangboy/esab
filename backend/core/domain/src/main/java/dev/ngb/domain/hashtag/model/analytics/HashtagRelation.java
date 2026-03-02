package dev.ngb.domain.hashtag.model.analytics;

import dev.ngb.domain.DomainEntity;

/**
 * Co-occurrence relationship between two hashtags, tracking how often they appear together.
 */
public class HashtagRelation extends DomainEntity<Long> {

    private Long hashtagAId;
    private Long hashtagBId;
    private Long coOccurrenceCount;
}
