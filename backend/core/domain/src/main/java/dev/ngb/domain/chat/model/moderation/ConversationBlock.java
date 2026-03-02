package dev.ngb.domain.chat.model.moderation;

import dev.ngb.domain.DomainEntity;

/**
 * Represents a profile blocking another within a conversation context.
 */
public class ConversationBlock extends DomainEntity<Long> {

    private Long blockerProfileId;
    private Long blockedProfileId;
    private Long conversationId;
}
