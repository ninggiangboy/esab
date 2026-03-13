package dev.ngb.infrastructure.jdbc.thread.repository;

import dev.ngb.domain.thread.model.interaction.ThreadBookmark;
import dev.ngb.domain.thread.repository.ThreadBookmarkRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadBookmarkJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadBookmarkJdbcRepository extends JdbcRepository<ThreadBookmark, ThreadBookmarkJdbcEntity, Long> implements ThreadBookmarkRepository {

    public ThreadBookmarkJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ThreadBookmarkJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ThreadBookmark mapToDomain(ThreadBookmarkJdbcEntity entity) {
        return ThreadBookmark.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getProfileId()
        );
    }

    @Override
    protected ThreadBookmarkJdbcEntity mapToJdbc(ThreadBookmark domain) {
        return ThreadBookmarkJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .profileId(domain.getProfileId())
                .build();
    }
}
