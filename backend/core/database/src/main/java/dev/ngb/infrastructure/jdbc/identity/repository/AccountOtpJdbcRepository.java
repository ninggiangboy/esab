package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountOtpJdbcEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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
        Criteria criteria = Criteria.where("account_id").is(accountId)
                .and("purpose").is(purpose.name())
                .and("is_used").is(false)
                .and("expires_at").greaterThan(Instant.now());
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return findOneBySorted(criteria, sort);
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
