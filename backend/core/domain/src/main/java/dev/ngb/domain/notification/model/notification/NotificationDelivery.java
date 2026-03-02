package dev.ngb.domain.notification.model.notification;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Tracks delivery status of a notification across channels (in-app, push, email).
 */
public class NotificationDelivery extends DomainEntity<Long> {

    private Long notificationId;
    private NotificationChannel channel;
    private NotificationDeliveryStatus status;
    private Instant sentAt;
}
