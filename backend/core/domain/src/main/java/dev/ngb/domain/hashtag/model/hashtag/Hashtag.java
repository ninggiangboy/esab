package dev.ngb.domain.hashtag.model.hashtag;

import dev.ngb.domain.DomainEntity;

/**
 * A hashtag used for content indexing and discovery, with normalized form for search.
 * <p>
 * Aggregate root of the hashtag domain. Owns stats, metadata, and moderation state.
 * Relations (co-occurrence graph) and hourly usage buckets are separate analytical entities.
 */
public class Hashtag extends DomainEntity<Long> {

    private String tag;
    private String normalizedTag;
    private Long usageCount;
    private Long threadCount;

    private HashtagStats stats;
    private HashtagMetadata metadata;
    private HashtagModeration moderation;
}
