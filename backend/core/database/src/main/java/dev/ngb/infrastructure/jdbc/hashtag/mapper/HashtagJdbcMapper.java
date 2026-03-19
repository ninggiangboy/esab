package dev.ngb.infrastructure.jdbc.hashtag.mapper;

import dev.ngb.domain.hashtag.model.hashtag.Hashtag;
import dev.ngb.domain.hashtag.model.hashtag.HashtagMetadata;
import dev.ngb.domain.hashtag.model.hashtag.HashtagModeration;
import dev.ngb.domain.hashtag.model.hashtag.HashtagStats;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagMetadataJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagModerationJdbcEntity;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagStatsJdbcEntity;

public final class HashtagJdbcMapper implements JdbcMapper<Hashtag, HashtagJdbcEntity> {

    public static final HashtagJdbcMapper INSTANCE = new HashtagJdbcMapper();

    private HashtagJdbcMapper() {}

    @Override
    public Hashtag toDomain(HashtagJdbcEntity entity) {
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
    public HashtagJdbcEntity toJdbc(Hashtag domain) {
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

    private static HashtagStats mapStatsToDomain(HashtagStatsJdbcEntity entity) {
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

    private static HashtagStatsJdbcEntity mapStatsToJdbc(HashtagStats domain) {
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

    private static HashtagMetadata mapMetadataToDomain(HashtagMetadataJdbcEntity entity) {
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

    private static HashtagMetadataJdbcEntity mapMetadataToJdbc(HashtagMetadata domain) {
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

    private static HashtagModeration mapModerationToDomain(HashtagModerationJdbcEntity entity) {
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

    private static HashtagModerationJdbcEntity mapModerationToJdbc(HashtagModeration domain) {
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

