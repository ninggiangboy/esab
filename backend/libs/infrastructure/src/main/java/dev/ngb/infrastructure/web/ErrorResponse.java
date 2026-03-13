package dev.ngb.infrastructure.web;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard error response body")
public record ErrorResponse(
        @Schema(description = "Error code identifier", example = "ACCOUNT_NOT_FOUND")
        String error,

        @Schema(description = "Human-readable error message", example = "Account not found")
        String message,

        @Schema(description = "Timestamp of the error occurrence")
        Instant timestamp,

        @Schema(description = "Additional error details", nullable = true)
        Map<String, Object> details
) {
}
