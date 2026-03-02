package dev.ngb.domain.thread.model.interaction;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile liking a thread.
 */
public class ThreadLike extends DomainEntity<Long> {

    private Long threadId;
    private Long profileId;
}
