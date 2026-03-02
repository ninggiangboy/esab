package dev.ngb.domain.chat.model.message;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;
import java.util.List;

/**
 * A message within a conversation, supporting text, media, thread shares, and edit/delete.
 * <p>
 * Aggregate root for message-level data. Owns media attachments and reactions.
 * Reports are a moderation concern managed separately.
 */
public class Message extends DomainEntity<Long> {

    private Long conversationId;
    private Long senderProfileId;
    private MessageType type;
    private String content;
    private Instant editedAt;
    private Instant deletedAt;

    private List<MessageMedia> medias;
    private List<MessageReaction> reactions;
}
