package dev.ngb.app.attachment.application.usecase.presign_attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Presigned PUT details and persisted attachment metadata")
public record PresignAttachmentResponse(
        @Schema(description = "Database id of the attachment row")
        Long attachmentId,

        @Schema(description = "Public uuid of the attachment")
        String attachmentUuid,

        @Schema(description = "HTTP PUT URL including signature query parameters")
        String uploadUrl,

        @Schema(description = "Headers the client must send with the PUT (e.g. Content-Type)")
        Map<String, String> uploadHeaders,

        @Schema(description = "Object key assigned for this upload")
        String objectKey,

        @Schema(description = "Public or canonical URL for the object after upload")
        String fileUrl,

        @Schema(description = "When the presigned URL stops being valid")
        Instant expiresAt
) {
}
