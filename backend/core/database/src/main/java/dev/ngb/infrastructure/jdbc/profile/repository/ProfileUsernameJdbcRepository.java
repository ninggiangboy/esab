package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.username.ProfileUsername;
import dev.ngb.domain.profile.repository.ProfileUsernameRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileUsernameJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileUsernameJdbcRepository
        extends JdbcRepository<ProfileUsername, ProfileUsernameJdbcEntity, Long>
        implements ProfileUsernameRepository {

    public ProfileUsernameJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileUsernameJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileUsername mapToDomain(ProfileUsernameJdbcEntity entity) {
        return ProfileUsername.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getUsername(),
                entity.getIsCurrent()
        );
    }

    @Override
    protected ProfileUsernameJdbcEntity mapToJdbc(ProfileUsername domain) {
        return ProfileUsernameJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .username(domain.getUsername())
                .isCurrent(domain.getIsCurrent())
                .build();
    }

    @Override
    public List<ProfileUsername> findByProfileId(Long profileId) {
        return findAllByField("profile_id", profileId);
    }
}
