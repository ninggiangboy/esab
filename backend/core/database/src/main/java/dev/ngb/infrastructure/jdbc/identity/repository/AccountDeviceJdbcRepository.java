package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.auth.AccountDevice;
import dev.ngb.domain.identity.repository.AccountDeviceRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountDeviceJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class AccountDeviceJdbcRepository
        extends JdbcRepository<AccountDevice, AccountDeviceJdbcEntity, Long>
        implements AccountDeviceRepository {

    public AccountDeviceJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountDeviceJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected AccountDevice mapToDomain(AccountDeviceJdbcEntity entity) {
        return AccountDevice.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getDeviceType(),
                entity.getDeviceName(),
                entity.getFingerprint(),
                entity.getUserAgent(),
                entity.getPushToken(),
                entity.getLastActiveAt(),
                entity.getIsTrusted()
        );
    }

    @Override
    protected AccountDeviceJdbcEntity mapToJdbc(AccountDevice domain) {
        return AccountDeviceJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .deviceType(domain.getDeviceType())
                .deviceName(domain.getDeviceName())
                .fingerprint(domain.getFingerprint())
                .userAgent(domain.getUserAgent())
                .pushToken(domain.getPushToken())
                .lastActiveAt(domain.getLastActiveAt())
                .isTrusted(domain.getIsTrusted())
                .build();
    }

    @Override
    public Optional<AccountDevice> findByAccountIdAndFingerprint(Long accountId, String fingerprint) {
        String sql = """
                SELECT *
                FROM iam_account_devices
                WHERE account_id = :accountId
                  AND fingerprint = :fingerprint
                """;
        return findOneBySql(sql, Map.of(
                "accountId", accountId,
                "fingerprint", fingerprint
        ));
    }
}

