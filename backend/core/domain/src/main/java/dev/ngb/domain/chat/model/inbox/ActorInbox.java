package dev.ngb.domain.chat.model.inbox;

import dev.ngb.domain.DomainEntity;

/**
 * Denormalized inbox index per profile, enabling fast inbox loading with unread counts, pin, and mute state.
 */
public class ActorInbox extends DomainEntity<Long> {

    private Long profileId;
    private Long conversationId;
    private Long lastMessageId;
    private Long lastReadMessageId;
    private Long unreadCount;
    private Boolean pinned;
    private Boolean muted;
}
