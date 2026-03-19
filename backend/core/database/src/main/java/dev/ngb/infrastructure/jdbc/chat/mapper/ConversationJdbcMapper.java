package dev.ngb.infrastructure.jdbc.chat.mapper;

import dev.ngb.domain.chat.model.conversation.Conversation;
import dev.ngb.domain.chat.model.conversation.ConversationParticipant;
import dev.ngb.domain.chat.model.conversation.ConversationReadState;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationParticipantJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationReadStateJdbcEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class ConversationJdbcMapper implements JdbcMapper<Conversation, ConversationJdbcEntity> {

    public static final ConversationJdbcMapper INSTANCE = new ConversationJdbcMapper();

    private ConversationJdbcMapper() {}

    @Override
    public Conversation toDomain(ConversationJdbcEntity entity) {
        return Conversation.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getType(),
                entity.getCreatedByProfileId(),
                entity.getLastMessageId(),
                entity.getLastMessageAt(),
                entity.getParticipants() == null ? Set.of() : entity.getParticipants().stream().map(ConversationJdbcMapper::mapParticipantToDomain).collect(Collectors.toSet()),
                entity.getReadStates() == null ? Set.of() : entity.getReadStates().stream().map(ConversationJdbcMapper::mapReadStateToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    public ConversationJdbcEntity toJdbc(Conversation domain) {
        return ConversationJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .type(domain.getType())
                .createdByProfileId(domain.getCreatedByProfileId())
                .lastMessageId(domain.getLastMessageId())
                .lastMessageAt(domain.getLastMessageAt())
                .participants(domain.getParticipants() == null ? null : domain.getParticipants().stream().map(ConversationJdbcMapper::mapParticipantToJdbc).collect(Collectors.toSet()))
                .readStates(domain.getReadStates() == null ? null : domain.getReadStates().stream().map(ConversationJdbcMapper::mapReadStateToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private static ConversationParticipant mapParticipantToDomain(ConversationParticipantJdbcEntity entity) {
        return ConversationParticipant.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getConversationId(),
                entity.getProfileId(),
                entity.getRole(),
                entity.getJoinedAt(),
                entity.getLeftAt()
        );
    }

    private static ConversationParticipantJdbcEntity mapParticipantToJdbc(ConversationParticipant domain) {
        return ConversationParticipantJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .conversationId(domain.getConversationId())
                .profileId(domain.getProfileId())
                .role(domain.getRole())
                .joinedAt(domain.getJoinedAt())
                .leftAt(domain.getLeftAt())
                .build();
    }

    private static ConversationReadState mapReadStateToDomain(ConversationReadStateJdbcEntity entity) {
        return ConversationReadState.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getConversationId(),
                entity.getProfileId(),
                entity.getLastReadMessageId()
        );
    }

    private static ConversationReadStateJdbcEntity mapReadStateToJdbc(ConversationReadState domain) {
        return ConversationReadStateJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .conversationId(domain.getConversationId())
                .profileId(domain.getProfileId())
                .lastReadMessageId(domain.getLastReadMessageId())
                .build();
    }
}

