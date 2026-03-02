package dev.ngb.domain.identity.model.account;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;
import java.util.List;

/**
 * Core identity entity representing a user's authentication account, separated from their public profile.
 * <p>
 * Aggregate root of the identity domain. Owns credentials and devices;
 * sessions, OTPs, and login history are operational/audit entities accessed separately.
 */
public class Account extends DomainEntity<Long> {

    private String email;
    private String phoneNumber;
    private String passwordHash;
    private AccountStatus status;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean twoFactorEnabled;
    private Instant lastLoginAt;
    private String lastLoginIp;

    private List<AccountCredential> credentials;
    private List<AccountDevice> devices;
}
