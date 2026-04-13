package dev.ngb.app.attachment.application.port;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public interface PresignedPutObjectPort {

    /**
     * Configured bucket name for PUT requests (same bucket used when building the presigned result file URL).
     */
    String storageBucket();

    PresignedPutResult presignPut(PutObjectRequest putObjectRequest, Duration signatureDuration);

    record PresignedPutResult(
            String uploadUrl,
            Map<String, String> uploadHeaders,
            Instant expiresAt,
            String fileUrl,
            String bucket
    ) {
    }
}
