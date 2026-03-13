package dev.ngb.infrastructure.jdbc.thread.repository;

import dev.ngb.domain.thread.model.interaction.ThreadLike;
import dev.ngb.domain.thread.repository.ThreadLikeRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadLikeJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadLikeJdbcRepository extends JdbcRepository<ThreadLike, ThreadLikeJdbcEntity, Long> implements ThreadLikeRepository {

    public ThreadLikeJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ThreadLikeJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ThreadLike mapToDomain(ThreadLikeJdbcEntity entity) {
        return ThreadLike.reconstruct(
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
    protected ThreadLikeJdbcEntity mapToJdbc(ThreadLike domain) {
        return ThreadLikeJdbcEntity.builder()
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
