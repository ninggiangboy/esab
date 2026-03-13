package dev.ngb.infrastructure.jdbc.chat.repository;

import dev.ngb.domain.chat.model.moderation.MessageReport;
import dev.ngb.domain.chat.repository.MessageReportRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.chat.entity.MessageReportJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MessageReportJdbcRepository extends JdbcRepository<MessageReport, MessageReportJdbcEntity, Long> implements MessageReportRepository {

    public MessageReportJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(MessageReportJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected MessageReport mapToDomain(MessageReportJdbcEntity entity) {
        return MessageReport.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getMessageId(),
                entity.getReporterProfileId(),
                entity.getReason()
        );
    }

    @Override
    protected MessageReportJdbcEntity mapToJdbc(MessageReport domain) {
        return MessageReportJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .messageId(domain.getMessageId())
                .reporterProfileId(domain.getReporterProfileId())
                .reason(domain.getReason())
                .build();
    }
}
