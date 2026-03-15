package dev.ngb.infrastructure.jdbc.profile.repository;

import dev.ngb.domain.profile.model.profile.ProfileLink;
import dev.ngb.domain.profile.repository.ProfileLinkRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.profile.entity.ProfileLinkJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileLinkJdbcRepository
        extends JdbcRepository<ProfileLink, ProfileLinkJdbcEntity, Long>
        implements ProfileLinkRepository {

    public ProfileLinkJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ProfileLinkJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ProfileLink mapToDomain(ProfileLinkJdbcEntity entity) {
        return ProfileLink.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getProfileId(),
                entity.getType(),
                entity.getUrl(),
                entity.getOrderIndex()
        );
    }

    @Override
    protected ProfileLinkJdbcEntity mapToJdbc(ProfileLink domain) {
        return ProfileLinkJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .profileId(domain.getProfileId())
                .type(domain.getType())
                .url(domain.getUrl())
                .orderIndex(domain.getOrderIndex())
                .build();
    }

    @Override
    public List<ProfileLink> findByProfileId(Long profileId) {
        return findAllBy("profileId", profileId);
    }
}
