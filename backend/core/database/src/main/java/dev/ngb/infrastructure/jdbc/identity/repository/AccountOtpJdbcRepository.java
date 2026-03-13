package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountOtpJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class AccountOtpJdbcRepository extends JdbcRepository<AccountOtp, AccountOtpJdbcEntity, Long> implements AccountOtpRepository {

    public AccountOtpJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountOtpJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public Optional<AccountOtp> findLatestActiveByAccountIdAndPurpose(Long accountId, OtpPurpose purpose) {
        String sql = """
                SELECT * FROM iam_account_otps
                WHERE account_id = :accountId
                  AND purpose = :purpose
                  AND is_used = false
                  AND expires_at > NOW()
                ORDER BY created_at DESC
                LIMIT 1
                """;
        return findOneBySql(sql, Map.of("accountId", accountId, "purpose", purpose.name()));
    }

    @Override
    protected AccountOtp mapToDomain(AccountOtpJdbcEntity entity) {
        return AccountOtp.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getCode(),
                entity.getPurpose(),
                entity.getChannel(),
                entity.getExpiresAt(),
                entity.getIsUsed(),
                entity.getAttempts()
        );
    }

    @Override
    protected AccountOtpJdbcEntity mapToJdbc(AccountOtp domain) {
        return AccountOtpJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .code(domain.getCode())
                .purpose(domain.getPurpose())
                .channel(domain.getChannel())
                .expiresAt(domain.getExpiresAt())
                .isUsed(domain.getIsUsed())
                .attempts(domain.getAttempts())
                .build();
    }
}
