package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Poll attached to a thread, holding poll-level settings like expiration and multi-vote.
 */
public class ThreadPoll extends DomainEntity<Long> {

    private Long threadId;
    private Instant expiresAt;
    private Boolean allowMultipleVotes;
}
