package dev.ngb.domain.identity.model.account;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * Represents a known device associated with an account, used for session tracking and push notifications.
 */
public class AccountDevice extends DomainEntity<Long> {

    private Long accountId;
    private DeviceType deviceType;
    private String deviceName;
    private String fingerprint;
    private String userAgent;
    private String pushToken;
    private Instant lastActiveAt;
    private Boolean isTrusted;
}
