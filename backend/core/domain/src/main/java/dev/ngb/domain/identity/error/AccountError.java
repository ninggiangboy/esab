package dev.ngb.domain.identity.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountError implements DomainError {

    ACCOUNT_NOT_FOUND("Account not found"),
    EMAIL_ALREADY_EXISTS("Email is already registered"),
    INVALID_CREDENTIALS("Invalid email or password"),
    ACCOUNT_NOT_ACTIVE("Account is not active"),
    ACCOUNT_SUSPENDED("Account has been suspended"),
    ACCOUNT_BANNED("Account has been banned"),
    ACCOUNT_PENDING("Account email has not been verified"),
    INVALID_OTP("Invalid or expired verification code"),
    OTP_MAX_ATTEMPTS("Maximum OTP verification attempts exceeded"),
    EMAIL_ALREADY_VERIFIED("Email has already been verified"),
    INVALID_VERIFICATION_TOKEN("Invalid or expired verification token"),
    INVALID_OAUTH_TOKEN("Invalid OAuth provider token"),
    OAUTH_EMAIL_CONFLICT("Email is already linked to another account"),
    INVALID_REFRESH_TOKEN("Invalid or expired refresh token"),
    SESSION_REVOKED("Session has been revoked"),
    PASSWORD_SAME_AS_OLD("New password must be different from current password");

    private final String message;
}
