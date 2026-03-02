package dev.ngb.domain.thread.model.interaction;

import dev.ngb.domain.DomainEntity;

/**
 * A profile's vote on a thread poll option.
 */
public class ThreadPollVote extends DomainEntity<Long> {

    private Long threadId;
    private Long profileId;
    private Long optionId;
}
