package dev.ngb.domain.hashtag.model.hashtag;

import dev.ngb.domain.DomainEntity;

/**
 * Moderation state for a hashtag, allowing restriction or banning.
 */
public class HashtagModeration extends DomainEntity<Long> {

    private Long hashtagId;
    private HashtagModerationStatus status;
    private String reason;
}
