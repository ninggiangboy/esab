package dev.ngb.domain.notification.model.notification;

import dev.ngb.domain.DomainEntity;

/**
 * References an entity related to a notification, allowing multiple entity references per notification.
 */
public class NotificationObject extends DomainEntity<Long> {

    private Long notificationId;
    private NotificationEntityType entityType;
    private Long entityId;
}
