package dev.ngb.domain.chat.model.conversation;

import dev.ngb.domain.DomainEntity;
import dev.ngb.util.CollectionUtils;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A chat conversation (direct or group).
 * <p>
 * Aggregate root of the conversation domain. Owns participants and read states.
 * Messages form a separate aggregate. Blocks and inbox index are cross-aggregate.
 */
@Getter
public class Conversation extends DomainEntity<Long> {

    private Conversation() {}

    private ConversationType type;
    private Long createdByProfileId;
    private Long lastMessageId;
    private Instant lastMessageAt;

    private Set<ConversationParticipant> participants;
    private Set<ConversationReadState> readStates;

    public Set<ConversationParticipant> getParticipants() {
        return CollectionUtils.immutable(participants);
    }

    public Set<ConversationReadState> getReadStates() {
        return CollectionUtils.immutable(readStates);
    }

    public static Conversation reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            ConversationType type, Long createdByProfileId, Long lastMessageId, Instant lastMessageAt,
            Set<ConversationParticipant> participants, Set<ConversationReadState> readStates) {
        Conversation obj = new Conversation();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.type = type;
        obj.createdByProfileId = createdByProfileId;
        obj.lastMessageId = lastMessageId;
        obj.lastMessageAt = lastMessageAt;
        obj.participants = participants == null ? new HashSet<>() : new HashSet<>(participants);
        obj.readStates = readStates == null ? new HashSet<>() : new HashSet<>(readStates);
        return obj;
    }
}
