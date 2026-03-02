package dev.ngb.domain.thread.model.interaction;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile reposting (sharing) a thread.
 */
public class ThreadRepost extends DomainEntity<Long> {

    private Long threadId;
    private Long profileId;
}
