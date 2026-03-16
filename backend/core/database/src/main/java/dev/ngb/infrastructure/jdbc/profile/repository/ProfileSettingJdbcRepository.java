package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.setting.ProfileSetting;
import dev.ngb.domain.profile.repository.ProfileSettingRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileSettingJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileSettingJdbcRepository
        extends JdbcRepository<ProfileSetting, ProfileSettingJdbcEntity, Long>
        implements ProfileSettingRepository {

    public ProfileSettingJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileSettingJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileSetting mapToDomain(ProfileSettingJdbcEntity entity) {
        return ProfileSetting.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getAllowMentions(),
                entity.getAllowMessages(),
                entity.getAllowTagging(),
                entity.getShowActivityStatus()
        );
    }

    @Override
    protected ProfileSettingJdbcEntity mapToJdbc(ProfileSetting domain) {
        return ProfileSettingJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .allowMentions(domain.getAllowMentions())
                .allowMessages(domain.getAllowMessages())
                .allowTagging(domain.getAllowTagging())
                .showActivityStatus(domain.getShowActivityStatus())
                .build();
    }

    @Override
    public Optional<ProfileSetting> findByProfileId(Long profileId) {
        return findOneByField("profileId", profileId);
    }
}
