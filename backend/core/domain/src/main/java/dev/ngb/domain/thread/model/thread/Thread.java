package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

/**
 * A post in the social network. Supports replies (via parentThreadId), quotes, polls, media, and mentions.
 * <p>
 * Aggregate root of the thread domain. Owns mentions, hashtags, media, poll, and link preview.
 * Interactions (like, bookmark, repost, vote) and moderation are cross-aggregate.
 */
@Getter
public class Thread extends DomainEntity<Long> {

    private Thread() {}

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

    private Set<ThreadMention> mentions;
    private Set<ThreadHashtag> hashtags;
    private Set<ThreadMedia> medias;
    private ThreadPoll poll;
    private Set<ThreadPollOption> pollOptions;

    private ThreadLinkPreview preview;

    public static Thread reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            Long authorProfileId, String rawContent, Long parentThreadId, Long rootThreadId, Long quoteThreadId,
            Long likeCount, Long replyCount, Long repostCount, Long quoteCount, Long bookmarkCount, Long viewCount,
            ThreadStatus status, ThreadVisibility visibility, ReplyRestriction replyRestriction,
            Boolean isHideLikeCount, Boolean isEdited, Instant editedAt, Instant scheduledPublishAt,
            Boolean hasMentions, Boolean hasHashtags, Boolean hasMedias, Boolean hasPolls,
            Set<ThreadMention> mentions, Set<ThreadHashtag> hashtags, Set<ThreadMedia> medias,
            ThreadPoll poll, Set<ThreadPollOption> pollOptions, ThreadLinkPreview preview) {
        Thread obj = new Thread();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.authorProfileId = authorProfileId;
        obj.rawContent = rawContent;
        obj.parentThreadId = parentThreadId;
        obj.rootThreadId = rootThreadId;
        obj.quoteThreadId = quoteThreadId;
        obj.likeCount = likeCount;
        obj.replyCount = replyCount;
        obj.repostCount = repostCount;
        obj.quoteCount = quoteCount;
        obj.bookmarkCount = bookmarkCount;
        obj.viewCount = viewCount;
        obj.status = status;
        obj.visibility = visibility;
        obj.replyRestriction = replyRestriction;
        obj.isHideLikeCount = isHideLikeCount;
        obj.isEdited = isEdited;
        obj.editedAt = editedAt;
        obj.scheduledPublishAt = scheduledPublishAt;
        obj.hasMentions = hasMentions;
        obj.hasHashtags = hasHashtags;
        obj.hasMedias = hasMedias;
        obj.hasPolls = hasPolls;
        obj.mentions = mentions;
        obj.hashtags = hashtags;
        obj.medias = medias;
        obj.poll = poll;
        obj.pollOptions = pollOptions;
        obj.preview = preview;
        return obj;
    }
}
