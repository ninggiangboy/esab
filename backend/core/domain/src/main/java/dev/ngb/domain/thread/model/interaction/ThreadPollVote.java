package dev.ngb.domain.thread.model.interaction;

import dev.ngb.domain.DomainEntity;
import lombok.Getter;

import java.time.Instant;

/**
 * A profile's vote on a thread poll option.
 */
@Getter
public class ThreadPollVote extends DomainEntity<Long> {

    private ThreadPollVote() {}

    private Long threadId;
    private Long profileId;
    private Long optionId;

    public static ThreadPollVote reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            Long threadId, Long profileId, Long optionId) {
        ThreadPollVote obj = new ThreadPollVote();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.threadId = threadId;
        obj.profileId = profileId;
        obj.optionId = optionId;
        return obj;
    }
}
