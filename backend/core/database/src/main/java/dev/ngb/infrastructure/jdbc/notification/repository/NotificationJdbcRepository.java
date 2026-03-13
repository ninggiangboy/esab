package dev.ngb.infrastructure.jdbc.notification.repository;

import dev.ngb.domain.notification.model.notification.Notification;
import dev.ngb.domain.notification.model.notification.NotificationActor;
import dev.ngb.domain.notification.model.notification.NotificationDelivery;
import dev.ngb.domain.notification.model.notification.NotificationObject;
import dev.ngb.domain.notification.repository.NotificationRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationActorJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationDeliveryJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationJdbcEntity;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationObjectJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class NotificationJdbcRepository extends JdbcRepository<Notification, NotificationJdbcEntity, Long> implements NotificationRepository {

    public NotificationJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(NotificationJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected Notification mapToDomain(NotificationJdbcEntity entity) {
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
                entity.getActors() == null ? Set.of() : entity.getActors().stream().map(this::mapActorToDomain).collect(Collectors.toSet()),
                entity.getObjects() == null ? Set.of() : entity.getObjects().stream().map(this::mapObjectToDomain).collect(Collectors.toSet()),
                entity.getDeliveries() == null ? Set.of() : entity.getDeliveries().stream().map(this::mapDeliveryToDomain).collect(Collectors.toSet())
        );
    }

    @Override
    protected NotificationJdbcEntity mapToJdbc(Notification domain) {
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
                .actors(domain.getActors() == null ? null : domain.getActors().stream().map(this::mapActorToJdbc).collect(Collectors.toSet()))
                .objects(domain.getObjects() == null ? null : domain.getObjects().stream().map(this::mapObjectToJdbc).collect(Collectors.toSet()))
                .deliveries(domain.getDeliveries() == null ? null : domain.getDeliveries().stream().map(this::mapDeliveryToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private NotificationActor mapActorToDomain(NotificationActorJdbcEntity entity) {
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

    private NotificationActorJdbcEntity mapActorToJdbc(NotificationActor domain) {
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

    private NotificationObject mapObjectToDomain(NotificationObjectJdbcEntity entity) {
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

    private NotificationObjectJdbcEntity mapObjectToJdbc(NotificationObject domain) {
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

    private NotificationDelivery mapDeliveryToDomain(NotificationDeliveryJdbcEntity entity) {
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

    private NotificationDeliveryJdbcEntity mapDeliveryToJdbc(NotificationDelivery domain) {
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
