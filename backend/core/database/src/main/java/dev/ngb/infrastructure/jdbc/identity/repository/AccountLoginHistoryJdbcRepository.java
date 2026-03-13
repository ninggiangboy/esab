package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.session.AccountLoginHistory;
import dev.ngb.domain.identity.repository.AccountLoginHistoryRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountLoginHistoryJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class AccountLoginHistoryJdbcRepository extends JdbcRepository<AccountLoginHistory, AccountLoginHistoryJdbcEntity, Long> implements AccountLoginHistoryRepository {

    public AccountLoginHistoryJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountLoginHistoryJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected AccountLoginHistory mapToDomain(AccountLoginHistoryJdbcEntity entity) {
        return AccountLoginHistory.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getDeviceId(),
                entity.getIpAddress(),
                entity.getUserAgent(),
                entity.getResult(),
                entity.getFailureReason()
        );
    }

    @Override
    protected AccountLoginHistoryJdbcEntity mapToJdbc(AccountLoginHistory domain) {
        return AccountLoginHistoryJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .deviceId(domain.getDeviceId())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .result(domain.getResult())
                .failureReason(domain.getFailureReason())
                .build();
    }
}
