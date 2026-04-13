package dev.ngb.app.attachment.infrastructure;

import dev.ngb.app.attachment.application.port.PresignedPutObjectPort;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AwsS3PresignedPutAdapter implements PresignedPutObjectPort {

    private final S3Presigner s3Presigner;
    private final S3StorageProperties storageProperties;

    public AwsS3PresignedPutAdapter(S3Presigner s3Presigner, S3StorageProperties storageProperties) {
        this.s3Presigner = s3Presigner;
        this.storageProperties = storageProperties;
    }

    @Override
    public String storageBucket() {
        return storageProperties.getBucket();
    }

    @Override
    public PresignedPutResult presignPut(PutObjectRequest putObjectRequest, Duration signatureDuration) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(signatureDuration)
                .putObjectRequest(putObjectRequest)
                .build();
        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);
        SdkHttpRequest http = presigned.httpRequest();
        Map<String, String> headers = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> e : http.headers().entrySet()) {
            if (!e.getValue().isEmpty()) {
                headers.put(e.getKey(), String.join(", ", e.getValue()));
            }
        }
        Instant expiresAt = presigned.expiration();
        String objectKey = putObjectRequest.key();
        String bucket = putObjectRequest.bucket();
        String fileUrl = buildPublicFileUrl(objectKey);
        return new PresignedPutResult(
                presigned.url().toExternalForm(),
                headers,
                expiresAt,
                fileUrl,
                bucket
        );
    }

    private String buildPublicFileUrl(String objectKey) {
        if (storageProperties.getPublicBaseUrl() != null && !storageProperties.getPublicBaseUrl().isBlank()) {
            String base = storageProperties.getPublicBaseUrl().replaceAll("/+$", "");
            return base + "/" + objectKey;
        }
        return S3Utilities.builder()
                .region(Region.of(storageProperties.getRegion()))
                .build()
                .getUrl(GetUrlRequest.builder()
                        .bucket(storageProperties.getBucket())
                        .key(objectKey)
                        .build())
                .toExternalForm();
    }
}
