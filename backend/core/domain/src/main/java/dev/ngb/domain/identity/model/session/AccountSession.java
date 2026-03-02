package dev.ngb.domain.identity.model.session;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Active login session tied to an account and device, with refresh token lifecycle.
 */
public class AccountSession extends DomainEntity<Long> {

    private Long accountId;
    private Long deviceId;
    private String tokenHash;
    private String ipAddress;
    private Instant expiresAt;
    private Boolean isRevoked;
}

