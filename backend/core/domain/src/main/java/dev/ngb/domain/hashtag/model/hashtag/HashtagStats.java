package dev.ngb.domain.hashtag.model.hashtag;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Denormalized usage counters for a hashtag, updated asynchronously.
 */
public class HashtagStats extends DomainEntity<Long> {

    private Long hashtagId;
    private Long threadCount;
    private Long usageCount;
    private Instant lastUsedAt;
}
