package dev.ngb.infrastructure.jdbc.notification.mapper;

import dev.ngb.domain.notification.model.notification.Notification;
import dev.ngb.domain.notification.model.notification.NotificationActor;
import dev.ngb.domain.notification.model.notification.NotificationDelivery;
import dev.ngb.domain.notification.model.notification.NotificationObject;
import dev.ngb.infrastructure.jdbc.base.mapper.JdbcMapper;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationActorJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationDeliveryJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationObjectJdbcEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class NotificationJdbcMapper implements JdbcMapper<Notification, NotificationJdbcEntity> {

    public static final NotificationJdbcMapper INSTANCE = new NotificationJdbcMapper();

    private NotificationJdbcMapper() {}

    @Override
    public Notification toDomain(NotificationJdbcEntity entity) {
        return Notification.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getRecipientProfileId(),
                entity.getActorProfileId(),
                entity.getType(),
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getIsRead(),
                entity.getGroupKey(),
                entity.getActorCount(),
                entity.getLastActorProfileId(),
                entity.getActors() == null ? Set.of() : entity.getActors().stream().map(NotificationJdbcMapper::mapActorToDomain).collect(Collectors.toSet()),
                entity.getObjects() == null ? Set.of() : entity.getObjects().stream().map(NotificationJdbcMapper::mapObjectToDomain).collect(Collectors.toSet()),
                entity.getDeliveries() == null ? Set.of() : entity.getDeliveries().stream().map(NotificationJdbcMapper::mapDeliveryToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    public NotificationJdbcEntity toJdbc(Notification domain) {
        return NotificationJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .recipientProfileId(domain.getRecipientProfileId())
                .actorProfileId(domain.getActorProfileId())
                .type(domain.getType())
                .entityType(domain.getEntityType())
                .entityId(domain.getEntityId())
                .isRead(domain.getIsRead())
                .groupKey(domain.getGroupKey())
                .actorCount(domain.getActorCount())
                .lastActorProfileId(domain.getLastActorProfileId())
                .actors(domain.getActors() == null ? null : domain.getActors().stream().map(NotificationJdbcMapper::mapActorToJdbc).collect(Collectors.toSet()))
                .objects(domain.getObjects() == null ? null : domain.getObjects().stream().map(NotificationJdbcMapper::mapObjectToJdbc).collect(Collectors.toSet()))
                .deliveries(domain.getDeliveries() == null ? null : domain.getDeliveries().stream().map(NotificationJdbcMapper::mapDeliveryToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private static NotificationActor mapActorToDomain(NotificationActorJdbcEntity entity) {
        return NotificationActor.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getNotificationId(),
                entity.getActorProfileId()
        );
    }

    private static NotificationActorJdbcEntity mapActorToJdbc(NotificationActor domain) {
        return NotificationActorJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .notificationId(domain.getNotificationId())
                .actorProfileId(domain.getActorProfileId())
                .build();
    }

    private static NotificationObject mapObjectToDomain(NotificationObjectJdbcEntity entity) {
        return NotificationObject.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getNotificationId(),
                entity.getEntityType(),
                entity.getEntityId()
        );
    }

    private static NotificationObjectJdbcEntity mapObjectToJdbc(NotificationObject domain) {
        return NotificationObjectJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .notificationId(domain.getNotificationId())
                .entityType(domain.getEntityType())
                .entityId(domain.getEntityId())
                .build();
    }

    private static NotificationDelivery mapDeliveryToDomain(NotificationDeliveryJdbcEntity entity) {
        return NotificationDelivery.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getNotificationId(),
                entity.getChannel(),
                entity.getStatus(),
                entity.getSentAt()
        );
    }

    private static NotificationDeliveryJdbcEntity mapDeliveryToJdbc(NotificationDelivery domain) {
        return NotificationDeliveryJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .notificationId(domain.getNotificationId())
                .channel(domain.getChannel())
                .status(domain.getStatus())
                .sentAt(domain.getSentAt())
                .build();
    }
}

