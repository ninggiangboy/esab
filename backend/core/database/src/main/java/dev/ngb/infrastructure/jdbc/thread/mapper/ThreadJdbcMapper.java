package dev.ngb.infrastructure.jdbc.thread.mapper;

import dev.ngb.domain.thread.model.thread.Thread;
import dev.ngb.domain.thread.model.thread.ThreadHashtag;
import dev.ngb.domain.thread.model.thread.ThreadLinkPreview;
import dev.ngb.domain.thread.model.thread.ThreadMedia;
import dev.ngb.domain.thread.model.thread.ThreadMention;
import dev.ngb.domain.thread.model.thread.ThreadPoll;
import dev.ngb.domain.thread.model.thread.ThreadPollOption;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadHashtagJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadLinkPreviewJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadMediaJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadMentionJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadPollJdbcEntity;
import dev.ngb.infrastructure.jdbc.thread.entity.ThreadPollOptionJdbcEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class ThreadJdbcMapper implements JdbcMapper<Thread, ThreadJdbcEntity> {

    public static final ThreadJdbcMapper INSTANCE = new ThreadJdbcMapper();

    private ThreadJdbcMapper() {}

    @Override
    public Thread toDomain(ThreadJdbcEntity entity) {
        return Thread.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAuthorProfileId(),
                entity.getRawContent(),
                entity.getParentThreadId(),
                entity.getRootThreadId(),
                entity.getQuoteThreadId(),
                entity.getLikeCount(),
                entity.getReplyCount(),
                entity.getRepostCount(),
                entity.getQuoteCount(),
                entity.getBookmarkCount(),
                entity.getViewCount(),
                entity.getStatus(),
                entity.getVisibility(),
                entity.getReplyRestriction(),
                entity.getIsHideLikeCount(),
                entity.getIsEdited(),
                entity.getEditedAt(),
                entity.getScheduledPublishAt(),
                entity.getHasMentions(),
                entity.getHasHashtags(),
                entity.getHasMedias(),
                entity.getHasPolls(),
                entity.getMentions() == null ? Set.of() : entity.getMentions().stream().map(ThreadJdbcMapper::mapMentionToDomain).collect(Collectors.toSet()),
                entity.getHashtags() == null ? Set.of() : entity.getHashtags().stream().map(ThreadJdbcMapper::mapHashtagToDomain).collect(Collectors.toSet()),
                entity.getMedias() == null ? Set.of() : entity.getMedias().stream().map(ThreadJdbcMapper::mapMediaToDomain).collect(Collectors.toSet()),
                entity.getPoll() == null ? null : mapPollToDomain(entity.getPoll()),
                entity.getPollOptions() == null ? Set.of() : entity.getPollOptions().stream().map(ThreadJdbcMapper::mapPollOptionToDomain).collect(Collectors.toSet()),
                entity.getPreview() == null ? null : mapPreviewToDomain(entity.getPreview())
        );
    }

    @Override
    public ThreadJdbcEntity toJdbc(Thread domain) {
        return ThreadJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .authorProfileId(domain.getAuthorProfileId())
                .rawContent(domain.getRawContent())
                .parentThreadId(domain.getParentThreadId())
                .rootThreadId(domain.getRootThreadId())
                .quoteThreadId(domain.getQuoteThreadId())
                .likeCount(domain.getLikeCount())
                .replyCount(domain.getReplyCount())
                .repostCount(domain.getRepostCount())
                .quoteCount(domain.getQuoteCount())
                .bookmarkCount(domain.getBookmarkCount())
                .viewCount(domain.getViewCount())
                .status(domain.getStatus())
                .visibility(domain.getVisibility())
                .replyRestriction(domain.getReplyRestriction())
                .isHideLikeCount(domain.getIsHideLikeCount())
                .isEdited(domain.getIsEdited())
                .editedAt(domain.getEditedAt())
                .scheduledPublishAt(domain.getScheduledPublishAt())
                .hasMentions(domain.getHasMentions())
                .hasHashtags(domain.getHasHashtags())
                .hasMedias(domain.getHasMedias())
                .hasPolls(domain.getHasPolls())
                .mentions(domain.getMentions() == null ? null : domain.getMentions().stream().map(ThreadJdbcMapper::mapMentionToJdbc).collect(Collectors.toSet()))
                .hashtags(domain.getHashtags() == null ? null : domain.getHashtags().stream().map(ThreadJdbcMapper::mapHashtagToJdbc).collect(Collectors.toSet()))
                .medias(domain.getMedias() == null ? null : domain.getMedias().stream().map(ThreadJdbcMapper::mapMediaToJdbc).collect(Collectors.toSet()))
                .poll(domain.getPoll() == null ? null : mapPollToJdbc(domain.getPoll()))
                .pollOptions(domain.getPollOptions() == null ? null : domain.getPollOptions().stream().map(ThreadJdbcMapper::mapPollOptionToJdbc).collect(Collectors.toSet()))
                .preview(domain.getPreview() == null ? null : mapPreviewToJdbc(domain.getPreview()))
                .build();
    }

    private static ThreadMention mapMentionToDomain(ThreadMentionJdbcEntity entity) {
        return ThreadMention.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getMentionedProfileId()
        );
    }

    private static ThreadMentionJdbcEntity mapMentionToJdbc(ThreadMention domain) {
        return ThreadMentionJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .mentionedProfileId(domain.getMentionedProfileId())
                .build();
    }

    private static ThreadHashtag mapHashtagToDomain(ThreadHashtagJdbcEntity entity) {
        return ThreadHashtag.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getHashtagId(),
                entity.getPositionStart(),
                entity.getPositionEnd()
        );
    }

    private static ThreadHashtagJdbcEntity mapHashtagToJdbc(ThreadHashtag domain) {
        return ThreadHashtagJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .hashtagId(domain.getHashtagId())
                .positionStart(domain.getPositionStart())
                .positionEnd(domain.getPositionEnd())
                .build();
    }

    private static ThreadMedia mapMediaToDomain(ThreadMediaJdbcEntity entity) {
        return ThreadMedia.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getType(),
                entity.getUrl(),
                entity.getThumbnailUrl(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getDuration(),
                entity.getAltText(),
                entity.getPosition()
        );
    }

    private static ThreadMediaJdbcEntity mapMediaToJdbc(ThreadMedia domain) {
        return ThreadMediaJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .type(domain.getType())
                .url(domain.getUrl())
                .thumbnailUrl(domain.getThumbnailUrl())
                .width(domain.getWidth())
                .height(domain.getHeight())
                .duration(domain.getDuration())
                .altText(domain.getAltText())
                .position(domain.getPosition())
                .build();
    }

    private static ThreadPoll mapPollToDomain(ThreadPollJdbcEntity entity) {
        return ThreadPoll.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getExpiresAt(),
                entity.getAllowMultipleVotes()
        );
    }

    private static ThreadPollJdbcEntity mapPollToJdbc(ThreadPoll domain) {
        return ThreadPollJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .expiresAt(domain.getExpiresAt())
                .allowMultipleVotes(domain.getAllowMultipleVotes())
                .build();
    }

    private static ThreadPollOption mapPollOptionToDomain(ThreadPollOptionJdbcEntity entity) {
        return ThreadPollOption.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getOption(),
                entity.getPosition(),
                entity.getVoteCount()
        );
    }

    private static ThreadPollOptionJdbcEntity mapPollOptionToJdbc(ThreadPollOption domain) {
        return ThreadPollOptionJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .option(domain.getOption())
                .position(domain.getPosition())
                .voteCount(domain.getVoteCount())
                .build();
    }

    private static ThreadLinkPreview mapPreviewToDomain(ThreadLinkPreviewJdbcEntity entity) {
        return ThreadLinkPreview.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getThreadId(),
                entity.getUrl(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getImageUrl()
        );
    }

    private static ThreadLinkPreviewJdbcEntity mapPreviewToJdbc(ThreadLinkPreview domain) {
        return ThreadLinkPreviewJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .threadId(domain.getThreadId())
                .url(domain.getUrl())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .build();
    }
}

