package dev.ngb.infrastructure.web;

import dev.ngb.domain.DomainErrorType;
import dev.ngb.domain.DomainException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ApiResponse(
            responseCode = "4xx/5xx",
            description = "Domain error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        HttpStatus status = mapToHttpStatus(ex.getError().getType());
        var body = new ErrorResponse(
                ex.getError().name(),
                ex.getError().getMessage(),
                Instant.now(),
                ex.getDetails() != null && !ex.getDetails().isEmpty() ? ex.getDetails() : null
        );
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus mapToHttpStatus(DomainErrorType type) {
        return switch (type) {
            case CLIENT -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE -> HttpStatus.CONFLICT;
            case VALIDATION -> HttpStatus.UNPROCESSABLE_ENTITY;
            case RATE_LIMITED -> HttpStatus.TOO_MANY_REQUESTS;
            case INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
        };
    }
}
