package dev.ngb.domain.thread.model.interaction;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile bookmarking a thread for later reference.
 */
public class ThreadBookmark extends DomainEntity<Long> {

    private Long threadId;
    private Long profileId;
}
