package dev.ngb.infrastructure.web;

import dev.ngb.domain.DomainException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard error response body")
public record ErrorResponse(
        @Schema(description = "Error code identifier")
        String error,

        @Schema(description = "Human-readable error message")
        String message,

        @Schema(description = "Timestamp of the error occurrence")
        Instant timestamp,

        @Schema(description = "Additional error details", nullable = true)
        Map<String, Object> details
) {
        public static ErrorResponse of(DomainException ex) {
                return new ErrorResponse(
                        ex.getError().name(),
                        ex.getError().getMessage(),
                        Instant.now(),
                        ex.getDetails()
                );
        }

        public static ErrorResponse of(String error, String message) {
                return new ErrorResponse(error, message, Instant.now(), null);
        }
}
