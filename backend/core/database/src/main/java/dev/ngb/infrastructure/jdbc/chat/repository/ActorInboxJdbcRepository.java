package dev.ngb.infrastructure.jdbc.chat.repository;

import dev.ngb.domain.chat.model.inbox.ActorInbox;
import dev.ngb.domain.chat.repository.ActorInboxRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.chat.entity.ActorInboxJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ActorInboxJdbcRepository extends JdbcRepository<ActorInbox, ActorInboxJdbcEntity, Long> implements ActorInboxRepository {

    public ActorInboxJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ActorInboxJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ActorInbox mapToDomain(ActorInboxJdbcEntity entity) {
        return ActorInbox.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getConversationId(),
                entity.getLastMessageId(),
                entity.getLastReadMessageId(),
                entity.getUnreadCount(),
                entity.getPinned(),
                entity.getMuted()
        );
    }

    @Override
    protected ActorInboxJdbcEntity mapToJdbc(ActorInbox domain) {
        return ActorInboxJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .conversationId(domain.getConversationId())
                .lastMessageId(domain.getLastMessageId())
                .lastReadMessageId(domain.getLastReadMessageId())
                .unreadCount(domain.getUnreadCount())
                .pinned(domain.getPinned())
                .muted(domain.getMuted())
                .build();
    }
}
