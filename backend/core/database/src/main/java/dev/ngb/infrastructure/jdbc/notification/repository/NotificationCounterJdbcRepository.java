package dev.ngb.infrastructure.jdbc.notification.repository;

import dev.ngb.domain.notification.model.profile.NotificationCounter;
import dev.ngb.domain.notification.repository.NotificationCounterRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationCounterJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationCounterJdbcRepository extends JdbcRepository<NotificationCounter, NotificationCounterJdbcEntity, Long> implements NotificationCounterRepository {

    public NotificationCounterJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(NotificationCounterJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected NotificationCounter mapToDomain(NotificationCounterJdbcEntity entity) {
        return NotificationCounter.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getUnreadCount()
        );
    }

    @Override
    protected NotificationCounterJdbcEntity mapToJdbc(NotificationCounter domain) {
        return NotificationCounterJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .unreadCount(domain.getUnreadCount())
                .build();
    }
}
