package dev.ngb.infrastructure.jdbc.hashtag.entity;

import dev.ngb.infrastructure.jdbc.base.entity.SoftDeletableJdbcEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table("ht_hashtags")
public class HashtagJdbcEntity extends SoftDeletableJdbcEntity<Long> {

    private String tag;
    private String normalizedTag;
    private Long usageCount;
    private Long threadCount;

    @MappedCollection(idColumn = "hashtag_id")
    private HashtagStatsJdbcEntity stats;

    @MappedCollection(idColumn = "hashtag_id")
    private HashtagMetadataJdbcEntity metadata;

    @MappedCollection(idColumn = "hashtag_id")
    private HashtagModerationJdbcEntity moderation;
}
