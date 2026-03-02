package dev.ngb.domain.identity.model.session;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.identity.model.session.LoginResult;

/**
 * Audit trail recording every login attempt for an account, including device and result.
 */
public class AccountLoginHistory extends DomainEntity<Long> {

    private Long accountId;
    private Long deviceId;
    private String ipAddress;
    private String userAgent;
    private LoginResult result;
    private String failureReason;
}

