package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.auth.AccountCredential;
import dev.ngb.domain.identity.model.auth.AuthProvider;
import dev.ngb.domain.identity.repository.AccountCredentialRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountCredentialJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AccountCredentialJdbcRepository
        extends JdbcRepository<AccountCredential, AccountCredentialJdbcEntity, Long>
        implements AccountCredentialRepository {

    public AccountCredentialJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(AccountCredentialJdbcEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected AccountCredential mapToDomain(AccountCredentialJdbcEntity entity) {
        return AccountCredential.reconstruct(
                entity.getId(),
                entity.getUuid(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt(),
                entity.getAccountId(),
                entity.getProvider(),
                entity.getProviderAccountId(),
                entity.getAccessToken(),
                entity.getRefreshToken()
        );
    }

    @Override
    protected AccountCredentialJdbcEntity mapToJdbc(AccountCredential domain) {
        return AccountCredentialJdbcEntity.builder()
                .id(domain.getId())
                .uuid(domain.getUuid())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .accountId(domain.getAccountId())
                .provider(domain.getProvider())
                .providerAccountId(domain.getProviderAccountId())
                .accessToken(domain.getAccessToken())
                .refreshToken(domain.getRefreshToken())
                .build();
    }

    @Override
    public List<AccountCredential> findByAccountId(Long accountId) {
        return findAllByField("accountId", accountId);
    }

    @Override
    public boolean existsByAccountIdAndProvider(Long accountId, AuthProvider provider) {
        String sql = """
                SELECT COUNT(*) FROM iam_account_credentials
                WHERE account_id = :accountId AND provider = :provider
                """;
        Long count = countBySql(sql, Map.of("accountId", accountId, "provider", provider.name()));
        return count != null && count > 0;
    }
}
