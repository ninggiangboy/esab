package dev.ngb.domain.hashtag.model.analytics;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Hourly time-bucketed usage count for trending calculation and analytics.
 */
public class HashtagUsageHourly extends DomainEntity<Long> {

    private Long hashtagId;
    private Instant hourBucket;
    private Long count;
}
