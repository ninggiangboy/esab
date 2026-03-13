package dev.ngb.infrastructure.jdbc.thread.repository;

import dev.ngb.domain.thread.model.interaction.ThreadRepost;
import dev.ngb.domain.thread.repository.ThreadRepostRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadRepostJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadRepostJdbcRepository extends JdbcRepository<ThreadRepost, ThreadRepostJdbcEntity, Long> implements ThreadRepostRepository {

    public ThreadRepostJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ThreadRepostJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ThreadRepost mapToDomain(ThreadRepostJdbcEntity entity) {
        return ThreadRepost.reconstruct(
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
    protected ThreadRepostJdbcEntity mapToJdbc(ThreadRepost domain) {
        return ThreadRepostJdbcEntity.builder()
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
