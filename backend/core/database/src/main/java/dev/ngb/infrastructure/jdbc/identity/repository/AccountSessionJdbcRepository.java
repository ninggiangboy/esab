package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountSessionJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountSessionJdbcRepository extends JdbcRepository<AccountSession, AccountSessionJdbcEntity, Long> implements AccountSessionRepository {

    public AccountSessionJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountSessionJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public Optional<AccountSession> findByTokenHash(String tokenHash) {
        return findOneByField("token_hash", tokenHash);
    }

    @Override
    public List<AccountSession> findActiveByAccountId(Long accountId) {
        Criteria criteria = Criteria.where("account_id").is(accountId)
                .and("is_revoked").is(false);
        return findAllBy(criteria);
    }

    @Override
    protected AccountSession mapToDomain(AccountSessionJdbcEntity entity) {
        return AccountSession.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getDeviceId(),
                entity.getTokenHash(),
                entity.getIpAddress(),
                entity.getExpiresAt(),
                entity.getIsRevoked()
        );
    }

    @Override
    protected AccountSessionJdbcEntity mapToJdbc(AccountSession domain) {
        return AccountSessionJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .deviceId(domain.getDeviceId())
                .tokenHash(domain.getTokenHash())
                .ipAddress(domain.getIpAddress())
                .expiresAt(domain.getExpiresAt())
                .isRevoked(domain.getIsRevoked())
                .build();
    }
}
