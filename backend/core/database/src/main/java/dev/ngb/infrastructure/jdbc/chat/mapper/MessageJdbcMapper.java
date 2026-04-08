package dev.ngb.infrastructure.jdbc.chat.mapper;

import dev.ngb.domain.chat.model.message.Message;
import dev.ngb.domain.chat.model.message.MessageMedia;
import dev.ngb.domain.chat.model.message.MessageReaction;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageMediaJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageReactionJdbcEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class MessageJdbcMapper implements JdbcMapper<Message, MessageJdbcEntity> {

    public static final MessageJdbcMapper INSTANCE = new MessageJdbcMapper();

    private MessageJdbcMapper() {}

    @Override
    public Message toDomain(MessageJdbcEntity entity) {
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
                entity.getIv(),
                entity.getEditedAt(),
                entity.getDeletedAt(),
                entity.getMedias() == null ? Set.of() : entity.getMedias().stream().map(MessageJdbcMapper::mapMediaToDomain).collect(Collectors.toSet()),
                entity.getReactions() == null ? Set.of() : entity.getReactions().stream().map(MessageJdbcMapper::mapReactionToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    public MessageJdbcEntity toJdbc(Message domain) {
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
                .iv(domain.getIv())
                .editedAt(domain.getEditedAt())
                .deletedAt(domain.getDeletedAt())
                .medias(domain.getMedias() == null ? null : domain.getMedias().stream().map(MessageJdbcMapper::mapMediaToJdbc).collect(Collectors.toSet()))
                .reactions(domain.getReactions() == null ? null : domain.getReactions().stream().map(MessageJdbcMapper::mapReactionToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private static MessageMedia mapMediaToDomain(MessageMediaJdbcEntity entity) {
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

    private static MessageMediaJdbcEntity mapMediaToJdbc(MessageMedia domain) {
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

    private static MessageReaction mapReactionToDomain(MessageReactionJdbcEntity entity) {
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

    private static MessageReactionJdbcEntity mapReactionToJdbc(MessageReaction domain) {
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

