package dev.ngb.domain.thread.model.moderation;

import dev.ngb.domain.DomainEntity;

/**
 * Report filed against a thread, with moderation workflow tracking.
 */
public class ThreadModeration extends DomainEntity<Long> {

    private Long threadId;
    private Long reporterProfileId;
    private String reason;

    private ThreadModerationStatus status;
    private Long moderatorProfileId;
    private String resolution;
}
