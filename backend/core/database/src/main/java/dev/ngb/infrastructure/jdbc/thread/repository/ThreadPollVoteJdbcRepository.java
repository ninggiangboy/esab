package dev.ngb.infrastructure.jdbc.thread.repository;

import dev.ngb.domain.thread.model.interaction.ThreadPollVote;
import dev.ngb.domain.thread.repository.ThreadPollVoteRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadPollVoteJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadPollVoteJdbcRepository extends JdbcRepository<ThreadPollVote, ThreadPollVoteJdbcEntity, Long> implements ThreadPollVoteRepository {

    public ThreadPollVoteJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ThreadPollVoteJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ThreadPollVote mapToDomain(ThreadPollVoteJdbcEntity entity) {
        return ThreadPollVote.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getProfileId(),
                entity.getOptionIndex()
        );
    }

    @Override
    protected ThreadPollVoteJdbcEntity mapToJdbc(ThreadPollVote domain) {
        return ThreadPollVoteJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .profileId(domain.getProfileId())
                .optionIndex(domain.getOptionIndex())
                .build();
    }
}
