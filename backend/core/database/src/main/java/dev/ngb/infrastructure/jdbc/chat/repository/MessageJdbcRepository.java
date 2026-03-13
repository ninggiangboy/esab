package dev.ngb.infrastructure.jdbc.chat.repository;

import dev.ngb.domain.chat.model.message.Message;
import dev.ngb.domain.chat.model.message.MessageMedia;
import dev.ngb.domain.chat.model.message.MessageReaction;
import dev.ngb.domain.chat.repository.MessageRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageMediaJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageReactionJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MessageJdbcRepository extends JdbcRepository<Message, MessageJdbcEntity, Long> implements MessageRepository {

    public MessageJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(MessageJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected Message mapToDomain(MessageJdbcEntity entity) {
        return Message.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getConversationId(),
                entity.getSenderProfileId(),
                entity.getType(),
                entity.getContent(),
                entity.getEditedAt(),
                entity.getDeletedAt(),
                entity.getMedias() == null ? Set.of() : entity.getMedias().stream().map(this::mapMediaToDomain).collect(Collectors.toSet()),
                entity.getReactions() == null ? Set.of() : entity.getReactions().stream().map(this::mapReactionToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    protected MessageJdbcEntity mapToJdbc(Message domain) {
        return MessageJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .conversationId(domain.getConversationId())
                .senderProfileId(domain.getSenderProfileId())
                .type(domain.getType())
                .content(domain.getContent())
                .editedAt(domain.getEditedAt())
                .deletedAt(domain.getDeletedAt())
                .medias(domain.getMedias() == null ? null : domain.getMedias().stream().map(this::mapMediaToJdbc).collect(Collectors.toSet()))
                .reactions(domain.getReactions() == null ? null : domain.getReactions().stream().map(this::mapReactionToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private MessageMedia mapMediaToDomain(MessageMediaJdbcEntity entity) {
        return MessageMedia.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getMessageId(),
                entity.getMediaId()
        );
    }

    private MessageMediaJdbcEntity mapMediaToJdbc(MessageMedia domain) {
        return MessageMediaJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .messageId(domain.getMessageId())
                .mediaId(domain.getMediaId())
                .build();
    }

    private MessageReaction mapReactionToDomain(MessageReactionJdbcEntity entity) {
        return MessageReaction.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getMessageId(),
                entity.getProfileId(),
                entity.getReaction()
        );
    }

    private MessageReactionJdbcEntity mapReactionToJdbc(MessageReaction domain) {
        return MessageReactionJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .messageId(domain.getMessageId())
                .profileId(domain.getProfileId())
                .reaction(domain.getReaction())
                .build();
    }
}
