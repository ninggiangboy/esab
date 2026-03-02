package dev.ngb.domain.notification.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Per-profile notification preferences controlling which types and channels are enabled.
 */
public class NotificationSetting extends DomainEntity<Long> {

    private Long profileId;

    private Boolean likeEnabled;
    private Boolean replyEnabled;
    private Boolean mentionEnabled;
    private Boolean followEnabled;
    private Boolean repostEnabled;
    private Boolean quoteEnabled;

    private Boolean pushEnabled;
    private Boolean emailEnabled;
}
