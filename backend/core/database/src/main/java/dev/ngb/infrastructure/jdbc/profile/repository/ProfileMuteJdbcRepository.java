package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.graph.ProfileMute;
import dev.ngb.domain.profile.repository.ProfileMuteRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileMuteJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileMuteJdbcRepository extends JdbcRepository<ProfileMute, ProfileMuteJdbcEntity, Long> implements ProfileMuteRepository {

    public ProfileMuteJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileMuteJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileMute mapToDomain(ProfileMuteJdbcEntity entity) {
        return ProfileMute.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getMuterProfileId(),
                entity.getMutedProfileId()
        );
    }

    @Override
    protected ProfileMuteJdbcEntity mapToJdbc(ProfileMute domain) {
        return ProfileMuteJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .muterProfileId(domain.getMuterProfileId())
                .mutedProfileId(domain.getMutedProfileId())
                .build();
    }
}
