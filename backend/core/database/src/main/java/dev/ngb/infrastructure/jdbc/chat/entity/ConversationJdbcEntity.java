package dev.ngb.infrastructure.jdbc.chat.entity;

import dev.ngb.domain.chat.model.conversation.ConversationType;
import dev.ngb.infrastructure.jdbc.base.entity.JdbcEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table("chat_conversations")
public class ConversationJdbcEntity extends JdbcEntity<Long> {

    private ConversationType type;
    private Long createdByProfileId;
    private Long lastMessageId;
    private Instant lastMessageAt;

    @MappedCollection(idColumn = "conversation_id")
    private Set<ConversationParticipantJdbcEntity> participants;

    @MappedCollection(idColumn = "conversation_id")
    private Set<ConversationReadStateJdbcEntity> readStates;
}
