package dev.ngb.infrastructure.jdbc.chat.repository;

import dev.ngb.domain.chat.model.conversation.Conversation;
import dev.ngb.domain.chat.model.conversation.ConversationParticipant;
import dev.ngb.domain.chat.model.conversation.ConversationReadState;
import dev.ngb.domain.chat.repository.ConversationRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationParticipantJdbcEntity;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationReadStateJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ConversationJdbcRepository extends JdbcRepository<Conversation, ConversationJdbcEntity, Long> implements ConversationRepository {

    public ConversationJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ConversationJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected Conversation mapToDomain(ConversationJdbcEntity entity) {
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
                entity.getParticipants() == null ? Set.of() : entity.getParticipants().stream().map(this::mapParticipantToDomain).collect(Collectors.toSet()),
                entity.getReadStates() == null ? Set.of() : entity.getReadStates().stream().map(this::mapReadStateToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    protected ConversationJdbcEntity mapToJdbc(Conversation domain) {
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
                .participants(domain.getParticipants() == null ? null : domain.getParticipants().stream().map(this::mapParticipantToJdbc).collect(Collectors.toSet()))
                .readStates(domain.getReadStates() == null ? null : domain.getReadStates().stream().map(this::mapReadStateToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private ConversationParticipant mapParticipantToDomain(ConversationParticipantJdbcEntity entity) {
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

    private ConversationParticipantJdbcEntity mapParticipantToJdbc(ConversationParticipant domain) {
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

    private ConversationReadState mapReadStateToDomain(ConversationReadStateJdbcEntity entity) {
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

    private ConversationReadStateJdbcEntity mapReadStateToJdbc(ConversationReadState domain) {
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
