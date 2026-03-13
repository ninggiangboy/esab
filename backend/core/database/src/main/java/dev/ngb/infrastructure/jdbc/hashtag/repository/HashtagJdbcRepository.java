package dev.ngb.infrastructure.jdbc.hashtag.repository;

import dev.ngb.domain.hashtag.model.hashtag.Hashtag;
import dev.ngb.domain.hashtag.model.hashtag.HashtagMetadata;
import dev.ngb.domain.hashtag.model.hashtag.HashtagModeration;
import dev.ngb.domain.hashtag.model.hashtag.HashtagStats;
import dev.ngb.domain.hashtag.repository.HashtagRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagMetadataJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagModerationJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagStatsJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class HashtagJdbcRepository extends JdbcRepository<Hashtag, HashtagJdbcEntity, Long> implements HashtagRepository {

    public HashtagJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(HashtagJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected Hashtag mapToDomain(HashtagJdbcEntity entity) {
        return Hashtag.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getTag(),
                entity.getNormalizedTag(),
                entity.getUsageCount(),
                entity.getThreadCount(),
                entity.getStats() == null ? null : mapStatsToDomain(entity.getStats()),
                entity.getMetadata() == null ? null : mapMetadataToDomain(entity.getMetadata()),
                entity.getModeration() == null ? null : mapModerationToDomain(entity.getModeration())
        );
    }

    @Override
    protected HashtagJdbcEntity mapToJdbc(Hashtag domain) {
        return HashtagJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .tag(domain.getTag())
                .normalizedTag(domain.getNormalizedTag())
                .usageCount(domain.getUsageCount())
                .threadCount(domain.getThreadCount())
                .stats(domain.getStats() == null ? null : mapStatsToJdbc(domain.getStats()))
                .metadata(domain.getMetadata() == null ? null : mapMetadataToJdbc(domain.getMetadata()))
                .moderation(domain.getModeration() == null ? null : mapModerationToJdbc(domain.getModeration()))
                .build();
    }

    private HashtagStats mapStatsToDomain(HashtagStatsJdbcEntity entity) {
        return HashtagStats.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getHashtagId(),
                entity.getThreadCount(),
                entity.getUsageCount(),
                entity.getLastUsedAt()
        );
    }

    private HashtagStatsJdbcEntity mapStatsToJdbc(HashtagStats domain) {
        return HashtagStatsJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .hashtagId(domain.getHashtagId())
                .threadCount(domain.getThreadCount())
                .usageCount(domain.getUsageCount())
                .lastUsedAt(domain.getLastUsedAt())
                .build();
    }

    private HashtagMetadata mapMetadataToDomain(HashtagMetadataJdbcEntity entity) {
        return HashtagMetadata.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getHashtagId(),
                entity.getCategory(),
                entity.getLanguage(),
                entity.getDescription()
        );
    }

    private HashtagMetadataJdbcEntity mapMetadataToJdbc(HashtagMetadata domain) {
        return HashtagMetadataJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .hashtagId(domain.getHashtagId())
                .category(domain.getCategory())
                .language(domain.getLanguage())
                .description(domain.getDescription())
                .build();
    }

    private HashtagModeration mapModerationToDomain(HashtagModerationJdbcEntity entity) {
        return HashtagModeration.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getHashtagId(),
                entity.getStatus(),
                entity.getReason()
        );
    }

    private HashtagModerationJdbcEntity mapModerationToJdbc(HashtagModeration domain) {
        return HashtagModerationJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .hashtagId(domain.getHashtagId())
                .status(domain.getStatus())
                .reason(domain.getReason())
                .build();
    }
}
