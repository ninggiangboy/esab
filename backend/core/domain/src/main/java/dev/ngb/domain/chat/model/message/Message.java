package dev.ngb.domain.chat.model.message;

import dev.ngb.domain.DomainEntity;
import dev.ngb.util.CollectionUtils;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A message within a conversation, supporting text, media, thread shares, and edit/delete.
 * <p>
 * Aggregate root for message-level data. Owns media attachments and reactions.
 * Reports are a moderation concern managed separately.
 */
@Getter
public class Message extends DomainEntity<Long> {

    private Message() {}

    private Long conversationId;
    private Long senderProfileId;
    private MessageType type;
    private String content;
    private Instant editedAt;
    private Instant deletedAt;

    private Set<MessageMedia> medias;
    private Set<MessageReaction> reactions;

    public Set<MessageMedia> getMedias() {
        return CollectionUtils.immutable(medias);
    }

    public Set<MessageReaction> getReactions() {
        return CollectionUtils.immutable(reactions);
    }

    public static Message reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            Long conversationId, Long senderProfileId, MessageType type, String content,
            Instant editedAt, Instant deletedAt, Set<MessageMedia> medias, Set<MessageReaction> reactions) {
        Message obj = new Message();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.conversationId = conversationId;
        obj.senderProfileId = senderProfileId;
        obj.type = type;
        obj.content = content;
        obj.editedAt = editedAt;
        obj.deletedAt = deletedAt;
        obj.medias = medias == null ? new HashSet<>() : new HashSet<>(medias);
        obj.reactions = reactions == null ? new HashSet<>() : new HashSet<>(reactions);
        return obj;
    }
}
