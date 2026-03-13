package dev.ngb.infrastructure.jdbc.hashtag.repository;

import dev.ngb.domain.hashtag.model.analytics.HashtagRelation;
import dev.ngb.domain.hashtag.repository.HashtagRelationRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.hashtag.entity.HashtagRelationJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class HashtagRelationJdbcRepository extends JdbcRepository<HashtagRelation, HashtagRelationJdbcEntity, Long> implements HashtagRelationRepository {

    public HashtagRelationJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(HashtagRelationJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected HashtagRelation mapToDomain(HashtagRelationJdbcEntity entity) {
        return HashtagRelation.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getHashtagAId(),
                entity.getHashtagBId(),
                entity.getCoOccurrenceCount()
        );
    }

    @Override
    protected HashtagRelationJdbcEntity mapToJdbc(HashtagRelation domain) {
        return HashtagRelationJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .hashtagAId(domain.getHashtagAId())
                .hashtagBId(domain.getHashtagBId())
                .coOccurrenceCount(domain.getCoOccurrenceCount())
                .build();
    }
}
