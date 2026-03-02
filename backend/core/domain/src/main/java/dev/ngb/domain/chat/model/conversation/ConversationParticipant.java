package dev.ngb.domain.chat.model.conversation;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * A participant in a conversation, with role and join/leave tracking.
 */
public class ConversationParticipant extends DomainEntity<Long> {

    private Long conversationId;
    private Long profileId;
    private ConversationRole role;
    private Instant joinedAt;
    private Instant leftAt;
}
