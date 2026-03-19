package dev.ngb.infrastructure.web;

import io.swagger.v3.oas.annotations.media.Schema;

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
}
