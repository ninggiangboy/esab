package dev.ngb.domain.chat.model.conversation;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;
import java.util.List;

/**
 * A chat conversation (direct or group).
 * <p>
 * Aggregate root of the conversation domain. Owns participants and read states.
 * Messages form a separate aggregate. Blocks and inbox index are cross-aggregate.
 */
public class Conversation extends DomainEntity<Long> {

    private ConversationType type;
    private Long createdByProfileId;
    private Long lastMessageId;
    private Instant lastMessageAt;

    private List<ConversationParticipant> participants;
    private List<ConversationReadState> readStates;
}
