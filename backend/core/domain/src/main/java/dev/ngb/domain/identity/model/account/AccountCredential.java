package dev.ngb.domain.identity.model.account;

import dev.ngb.domain.DomainEntity;

/**
 * Stores authentication credentials for an account, supporting multiple providers (local password, OAuth).
 */
public class AccountCredential extends DomainEntity<Long> {

    private Long accountId;
    private AuthProvider provider;
    private String providerAccountId;
    private String accessToken;
    private String refreshToken;
}
