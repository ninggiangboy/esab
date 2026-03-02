package dev.ngb.domain.notification.model.profile;

import dev.ngb.domain.DomainEntity;

/**
 * Materialized unread notification count per profile, backed by Redis cache at runtime.
 */
public class NotificationCounter extends DomainEntity<Long> {

    private Long profileId;
    private Long unreadCount;
}
