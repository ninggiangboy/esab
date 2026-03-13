package dev.ngb.infrastructure.jdbc.chat.repository;

import dev.ngb.domain.chat.model.moderation.ConversationBlock;
import dev.ngb.domain.chat.repository.ConversationBlockRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.chat.entity.ConversationBlockJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ConversationBlockJdbcRepository extends JdbcRepository<ConversationBlock, ConversationBlockJdbcEntity, Long> implements ConversationBlockRepository {

    public ConversationBlockJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ConversationBlockJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ConversationBlock mapToDomain(ConversationBlockJdbcEntity entity) {
        return ConversationBlock.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getBlockerProfileId(),
                entity.getBlockedProfileId(),
                entity.getConversationId()
        );
    }

    @Override
    protected ConversationBlockJdbcEntity mapToJdbc(ConversationBlock domain) {
        return ConversationBlockJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .blockerProfileId(domain.getBlockerProfileId())
                .blockedProfileId(domain.getBlockedProfileId())
                .conversationId(domain.getConversationId())
                .build();
    }
}
