package dev.ngb.domain;

public enum DomainErrorType {
    CLIENT,        // 400
    UNAUTHORIZED,  // 401
    FORBIDDEN,     // 403
    NOT_FOUND,     // 404
    DUPLICATE,     // 409
    VALIDATION,    // 422
    RATE_LIMITED,  // 429
    INTERNAL,      // 500
    UNAVAILABLE    // 503
}
