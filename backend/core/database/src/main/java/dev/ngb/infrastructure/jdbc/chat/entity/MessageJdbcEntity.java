package dev.ngb.infrastructure.jdbc.chat.entity;

import dev.ngb.domain.chat.model.message.MessageType;
import dev.ngb.infrastructure.jdbc.base.entity.SoftDeletableJdbcEntity;
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
@Table("chat_messages")
public class MessageJdbcEntity extends SoftDeletableJdbcEntity<Long> {

    private Long conversationId;
    private Long senderProfileId;
    private MessageType type;
    private String content;
    private Instant editedAt;

    @MappedCollection(idColumn = "message_id")
    private Set<MessageMediaJdbcEntity> medias;

    @MappedCollection(idColumn = "message_id")
    private Set<MessageReactionJdbcEntity> reactions;
}
