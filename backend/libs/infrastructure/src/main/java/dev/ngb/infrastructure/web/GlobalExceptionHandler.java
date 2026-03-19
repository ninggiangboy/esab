package dev.ngb.infrastructure.web;

import dev.ngb.domain.DomainErrorType;
import dev.ngb.domain.DomainException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ApiResponse(
            responseCode = "4xx",
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
            case INVALID -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case VALIDATION -> HttpStatus.UNPROCESSABLE_CONTENT;
            case RATE_LIMITED -> HttpStatus.TOO_MANY_REQUESTS;
        };
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternal(Exception ex) {

        log.error("Internal server error", ex);

        var body = new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected error occurred",
                Instant.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        var body = new ErrorResponse(
                "MISSING_PARAM",
                "Missing request parameter: " + ex.getParameterName(),
                Instant.now(),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMissingBody(HttpMessageNotReadableException ex) {
        var body = new ErrorResponse(
                "INVALID_BODY",
                "Request body is missing or malformed",
                Instant.now(),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var body = new ErrorResponse(
                "TYPE_MISMATCH",
                "Invalid value for parameter: " + ex.getName(),
                Instant.now(),
                null
        );
        return ResponseEntity.badRequest().body(body);
    }
}
