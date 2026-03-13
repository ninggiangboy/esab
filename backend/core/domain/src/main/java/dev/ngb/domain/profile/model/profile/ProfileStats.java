package dev.ngb.domain.profile.model.profile;

import dev.ngb.domain.DomainEntity;
import lombok.Getter;

import java.time.Instant;

/**
 * Denormalized counters for a profile, updated asynchronously.
 */
@Getter
public class ProfileStats extends DomainEntity<Long> {

    private ProfileStats() {}

    private Long profileId;
    private Long followerCount;
    private Long followingCount;
    private Long threadCount;
    private Long likeCount;
    private Long mediaCount;

    public static ProfileStats reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            Long profileId, Long followerCount, Long followingCount, Long threadCount,
            Long likeCount, Long mediaCount) {
        ProfileStats obj = new ProfileStats();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.profileId = profileId;
        obj.followerCount = followerCount;
        obj.followingCount = followingCount;
        obj.threadCount = threadCount;
        obj.likeCount = likeCount;
        obj.mediaCount = mediaCount;
        return obj;
    }
}
