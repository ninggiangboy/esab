package dev.ngb.infrastructure.jdbc.notification.repository;

import dev.ngb.domain.notification.model.profile.NotificationSetting;
import dev.ngb.domain.notification.repository.NotificationSettingRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.notification.entity.NotificationSettingJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationSettingJdbcRepository extends JdbcRepository<NotificationSetting, NotificationSettingJdbcEntity, Long> implements NotificationSettingRepository {

    public NotificationSettingJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(NotificationSettingJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected NotificationSetting mapToDomain(NotificationSettingJdbcEntity entity) {
        return NotificationSetting.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getLikeEnabled(),
                entity.getReplyEnabled(),
                entity.getMentionEnabled(),
                entity.getFollowEnabled(),
                entity.getRepostEnabled(),
                entity.getQuoteEnabled(),
                entity.getPushEnabled(),
                entity.getEmailEnabled()
        );
    }

    @Override
    protected NotificationSettingJdbcEntity mapToJdbc(NotificationSetting domain) {
        return NotificationSettingJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .likeEnabled(domain.getLikeEnabled())
                .replyEnabled(domain.getReplyEnabled())
                .mentionEnabled(domain.getMentionEnabled())
                .followEnabled(domain.getFollowEnabled())
                .repostEnabled(domain.getRepostEnabled())
                .quoteEnabled(domain.getQuoteEnabled())
                .pushEnabled(domain.getPushEnabled())
                .emailEnabled(domain.getEmailEnabled())
                .build();
    }
}
