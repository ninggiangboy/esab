package dev.ngb.app.attachment.infrastructure;

import dev.ngb.app.attachment.application.port.AttachmentObjectStoragePort;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@RequiredArgsConstructor
public class AwsS3AttachmentObjectStorageAdapter implements AttachmentObjectStoragePort {

    private final S3Client s3Client;

    @Override
    public boolean objectExists(String bucket, String objectKey) {
        try {
            s3Client.headObject(HeadObjectRequest.builder().bucket(bucket).key(objectKey).build());
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
            throw e;
        }
    }
}
