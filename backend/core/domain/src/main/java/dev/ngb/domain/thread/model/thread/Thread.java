package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;
import java.util.List;

/**
 * A post in the social network. Supports replies (via parentThreadId), quotes, polls, media, and mentions.
 * <p>
 * Aggregate root of the thread domain. Owns mentions, hashtags, media, poll, and link preview.
 * Interactions (like, bookmark, repost, vote) and moderation are cross-aggregate.
 */
public class Thread extends DomainEntity<Long> {

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

    private List<ThreadMention> mentions;
    private List<ThreadHashtag> hashtags;
    private List<ThreadMedia> medias;
    private ThreadPoll poll;
    private List<ThreadPollOption> pollOptions;

    private ThreadLinkPreview preview;
}
