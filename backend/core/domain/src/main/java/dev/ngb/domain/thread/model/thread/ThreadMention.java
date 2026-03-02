package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile being mentioned in a thread.
 */
public class ThreadMention extends DomainEntity<Long> {

    private Long threadId;
    private Long mentionedProfileId;
}
