package dev.ngb.infrastructure.jdbc.thread.repository;

import dev.ngb.domain.thread.model.moderation.ThreadModeration;
import dev.ngb.domain.thread.repository.ThreadModerationRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadModerationJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadModerationJdbcRepository extends JdbcRepository<ThreadModeration, ThreadModerationJdbcEntity, Long> implements ThreadModerationRepository {

    public ThreadModerationJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ThreadModerationJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ThreadModeration mapToDomain(ThreadModerationJdbcEntity entity) {
        return ThreadModeration.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getReporterProfileId(),
                entity.getReason(),
                entity.getStatus(),
                entity.getModeratorProfileId(),
                entity.getResolution()
        );
    }

    @Override
    protected ThreadModerationJdbcEntity mapToJdbc(ThreadModeration domain) {
        return ThreadModerationJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .reporterProfileId(domain.getReporterProfileId())
                .reason(domain.getReason())
                .status(domain.getStatus())
                .moderatorProfileId(domain.getModeratorProfileId())
                .resolution(domain.getResolution())
                .build();
    }
}
