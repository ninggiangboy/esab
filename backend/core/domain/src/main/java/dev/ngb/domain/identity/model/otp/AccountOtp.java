package dev.ngb.domain.identity.model.otp;

import dev.ngb.domain.DomainEntity;

import java.time.Instant;

/**
 * One-time password issued for account verification, login, password reset, or two-factor authentication.
 */
public class AccountOtp extends DomainEntity<Long> {

    private Long accountId;
    private String code;
    private OtpPurpose purpose;
    private OtpChannel channel;
    private Instant expiresAt;
    private Boolean isUsed;
    private Integer attempts;
}

