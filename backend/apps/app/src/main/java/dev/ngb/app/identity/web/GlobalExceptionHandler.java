package dev.ngb.app.identity.web;

import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        HttpStatus status = mapToHttpStatus(ex);
        Map<String, Object> body = Map.of(
                "error", ex.getError().name(),
                "message", ex.getError().getMessage(),
                "timestamp", Instant.now().toString()
        );
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus mapToHttpStatus(DomainException ex) {
        if (ex.getError() instanceof AccountError error) {
            return switch (error) {
                case ACCOUNT_NOT_FOUND -> HttpStatus.NOT_FOUND;
                case EMAIL_ALREADY_EXISTS, EMAIL_ALREADY_VERIFIED, OAUTH_EMAIL_CONFLICT -> HttpStatus.CONFLICT;
                case INVALID_CREDENTIALS, INVALID_REFRESH_TOKEN, INVALID_VERIFICATION_TOKEN,
                     INVALID_OAUTH_TOKEN, SESSION_REVOKED -> HttpStatus.UNAUTHORIZED;
                case ACCOUNT_SUSPENDED, ACCOUNT_BANNED, ACCOUNT_NOT_ACTIVE, ACCOUNT_PENDING -> HttpStatus.FORBIDDEN;
                case INVALID_OTP, OTP_MAX_ATTEMPTS, PASSWORD_SAME_AS_OLD -> HttpStatus.BAD_REQUEST;
            };
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
