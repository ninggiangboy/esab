package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

/**
 * A single option within a thread poll.
 */
public class ThreadPollOption extends DomainEntity<Long> {

    private Long threadId;
    private String option;
    private Integer position;
    private Long voteCount;
}
