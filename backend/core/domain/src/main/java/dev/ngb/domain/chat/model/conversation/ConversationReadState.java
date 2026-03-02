package dev.ngb.domain.chat.model.conversation;

import dev.ngb.domain.DomainEntity;

/**
 * Tracks the last read message per participant, more scalable than per-message read receipts.
 */
public class ConversationReadState extends DomainEntity<Long> {

    private Long conversationId;
    private Long profileId;
    private Long lastReadMessageId;
}
