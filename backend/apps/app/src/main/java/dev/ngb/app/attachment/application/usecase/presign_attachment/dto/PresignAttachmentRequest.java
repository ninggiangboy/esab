package dev.ngb.app.attachment.application.usecase.presign_attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request a presigned URL to upload a binary attachment")
public record PresignAttachmentRequest(
        @Schema(description = "Original file name (only the last segment is used)", example = "photo.png")
        String fileName,

        @Schema(description = "MIME type of the attachment", example = "image/png")
        String contentType,

        @Schema(description = "Declared size in bytes (required when max size is configured)", example = "1024")
        Long sizeBytes
) {
}
