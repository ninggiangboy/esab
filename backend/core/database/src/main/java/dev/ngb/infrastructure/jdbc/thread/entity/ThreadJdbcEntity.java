package dev.ngb.infrastructure.jdbc.thread.entity;

import dev.ngb.domain.thread.model.thread.ReplyRestriction;
import dev.ngb.domain.thread.model.thread.ThreadStatus;
import dev.ngb.domain.thread.model.thread.ThreadVisibility;
import dev.ngb.infrastructure.jdbc.base.entity.JdbcEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table("thr_threads")
public class ThreadJdbcEntity extends JdbcEntity<Long> {

    private Long authorProfileId;
    private String rawContent;
    private Long parentThreadId;
    private Long rootThreadId;
    private Long quoteThreadId;
    private Long likeCount;
    private Long replyCount;
    private Long repostCount;
    private Long quoteCount;
    private Long bookmarkCount;
    private Long viewCount;
    private ThreadStatus status;
    private ThreadVisibility visibility;
    private ReplyRestriction replyRestriction;
    private Boolean isHideLikeCount;
    private Boolean isEdited;
    private Instant editedAt;
    private Instant scheduledPublishAt;
    private Boolean hasMentions;
    private Boolean hasHashtags;
    private Boolean hasMedias;
    private Boolean hasPolls;

    @MappedCollection(idColumn = "thread_id")
    private Set<ThreadMentionJdbcEntity> mentions;

    @MappedCollection(idColumn = "thread_id")
    private Set<ThreadHashtagJdbcEntity> hashtags;

    @MappedCollection(idColumn = "thread_id")
    private Set<ThreadMediaJdbcEntity> medias;

    @MappedCollection(idColumn = "thread_id")
    private ThreadPollJdbcEntity poll;

    @MappedCollection(idColumn = "thread_id")
    private Set<ThreadPollOptionJdbcEntity> pollOptions;

    @MappedCollection(idColumn = "thread_id")
    private ThreadLinkPreviewJdbcEntity preview;
}
