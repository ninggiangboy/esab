package dev.ngb.app.attachment.application.port;

/**
 * Checks whether an object exists in external object storage.
 */
public interface AttachmentObjectStoragePort {

    boolean objectExists(String bucket, String objectKey);
}
