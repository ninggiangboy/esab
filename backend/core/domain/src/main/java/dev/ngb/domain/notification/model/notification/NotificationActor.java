package dev.ngb.domain.notification.model.notification;

import dev.ngb.domain.DomainEntity;

/**
 * Tracks individual actors within a grouped notification.
 */
public class NotificationActor extends DomainEntity<Long> {

    private Long notificationId;
    private Long actorProfileId;
}
