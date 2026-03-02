package dev.ngb.domain.chat.model.moderation;

import dev.ngb.domain.DomainEntity;

/**
 * Report filed against a message for moderation.
 */
public class MessageReport extends DomainEntity<Long> {

    private Long messageId;
    private Long reporterProfileId;
    private String reason;
}
