package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.auth.Account;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountJdbcRepository extends JdbcRepository<Account, AccountJdbcEntity, Long> implements AccountRepository {

    public AccountJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    public boolean existsByEmail(String email) {
        return existsBy("email", email);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return findOneBy("email", email);
    }

    @Override
    protected Account mapToDomain(AccountJdbcEntity entity) {
        return Account.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getPasswordHash(),
                entity.getStatus(),
                entity.getEmailVerified(),
                entity.getPhoneVerified(),
                entity.getTwoFactorEnabled(),
                entity.getLastLoginAt(),
                entity.getLastLoginIp()
        );
    }

    @Override
    protected AccountJdbcEntity mapToJdbc(Account domain) {
        return AccountJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .email(domain.getEmail())
                .phoneNumber(domain.getPhoneNumber())
                .passwordHash(domain.getPasswordHash())
                .status(domain.getStatus())
                .emailVerified(domain.getEmailVerified())
                .phoneVerified(domain.getPhoneVerified())
                .twoFactorEnabled(domain.getTwoFactorEnabled())
                .lastLoginAt(domain.getLastLoginAt())
                .lastLoginIp(domain.getLastLoginIp())
                .build();
    }
}
