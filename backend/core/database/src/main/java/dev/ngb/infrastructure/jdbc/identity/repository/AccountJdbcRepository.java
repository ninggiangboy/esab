package dev.ngb.infrastructure.jdbc.identity.repository;

import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountCredential;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.infrastructure.jdbc.base.helper.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.repository.JdbcRepository;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountCredentialJdbcEntity;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountDeviceJdbcEntity;
import dev.ngb.infrastructure.jdbc.identity.entity.AccountJdbcEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                entity.getLastLoginIp(),
                entity.getCredentials() == null ? Set.of() : entity.getCredentials().stream().map(this::mapCredentialToDomain).collect(Collectors.toSet()),
                entity.getDevices() == null ? Set.of() : entity.getDevices().stream().map(this::mapDeviceToDomain).collect(Collectors.toSet())
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
                .credentials(domain.getCredentials() == null ? null : domain.getCredentials().stream().map(this::mapCredentialToJdbc).collect(Collectors.toSet()))
                .devices(domain.getDevices() == null ? null : domain.getDevices().stream().map(this::mapDeviceToJdbc).collect(Collectors.toSet()))
                .build();
    }

    private AccountCredential mapCredentialToDomain(AccountCredentialJdbcEntity entity) {
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

    private AccountCredentialJdbcEntity mapCredentialToJdbc(AccountCredential domain) {
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

    private AccountDevice mapDeviceToDomain(AccountDeviceJdbcEntity entity) {
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

    private AccountDeviceJdbcEntity mapDeviceToJdbc(AccountDevice domain) {
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
}
