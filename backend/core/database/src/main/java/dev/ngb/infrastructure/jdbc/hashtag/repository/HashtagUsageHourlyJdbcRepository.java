package dev.ngb.infrastructure.jdbc.hashtag.repository;

import dev.ngb.domain.hashtag.model.analytics.HashtagUsageHourly;
import dev.ngb.domain.hashtag.repository.HashtagUsageHourlyRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagUsageHourlyJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class HashtagUsageHourlyJdbcRepository extends JdbcRepository<HashtagUsageHourly, HashtagUsageHourlyJdbcEntity, Long> implements HashtagUsageHourlyRepository {

    public HashtagUsageHourlyJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(HashtagUsageHourlyJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected HashtagUsageHourly mapToDomain(HashtagUsageHourlyJdbcEntity entity) {
        return HashtagUsageHourly.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getHashtagId(),
                entity.getHourBucket(),
                entity.getCount()
        );
    }

    @Override
    protected HashtagUsageHourlyJdbcEntity mapToJdbc(HashtagUsageHourly domain) {
        return HashtagUsageHourlyJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .hashtagId(domain.getHashtagId())
                .hourBucket(domain.getHourBucket())
                .count(domain.getCount())
                .build();
    }
}
