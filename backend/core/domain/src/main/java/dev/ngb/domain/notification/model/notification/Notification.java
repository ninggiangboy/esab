package dev.ngb.domain.notification.model.notification;

import dev.ngb.domain.DomainEntity;

import java.util.List;

/**
 * A notification delivered to a profile, with support for grouping multiple actors
 * (e.g. 'Alice and 12 others liked your thread').
 * <p>
 * Aggregate root of the notification domain. Owns actors, referenced objects, and delivery records.
 * Settings and counters are per-profile entities managed separately.
 */
public class Notification extends DomainEntity<Long> {

    private Long recipientProfileId;
    private Long actorProfileId;

    private NotificationType type;
    private NotificationEntityType entityType;
    private Long entityId;

    private Boolean isRead;

    private String groupKey;
    private Long actorCount;
    private Long lastActorProfileId;

    private List<NotificationActor> actors;
    private List<NotificationObject> objects;
    private List<NotificationDelivery> deliveries;
}
