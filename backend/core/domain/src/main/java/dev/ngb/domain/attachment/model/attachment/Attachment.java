package dev.ngb.domain.attachment.model.attachment;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.attachment.model.AttachmentUploadStatus;
import lombok.Getter;

import java.time.Instant;

/**
 * Binary attachment metadata for an account-owned object in external storage.
 */
@Getter
public class Attachment extends DomainEntity<Long> {

    private Long accountId;
    private String bucket;
    private String objectKey;
    private String fileName;
    private String contentType;
    private Long sizeBytes;
    private String fileUrl;
    private AttachmentUploadStatus uploadStatus;

    private Attachment() {
    }

    public static Attachment createPendingPut(
            Long accountId,
            String bucket,
            String objectKey,
            String fileName,
            String contentType,
            Long sizeBytes,
            String fileUrl,
            Long createdBy
    ) {
        Attachment a = new Attachment();
        Instant now = Instant.now(a.clock);
        a.accountId = accountId;
        a.bucket = bucket;
        a.objectKey = objectKey;
        a.fileName = fileName;
        a.contentType = contentType;
        a.sizeBytes = sizeBytes;
        a.fileUrl = fileUrl;
        a.uploadStatus = AttachmentUploadStatus.PENDING_PUT;
        a.createdBy = createdBy;
        a.updatedBy = createdBy;
        a.createdAt = now;
        a.updatedAt = now;
        return a;
    }

    public static Attachment reconstruct(
            Long id,
            String uuid,
            Long createdBy,
            Instant createdAt,
            Long updatedBy,
            Instant updatedAt,
            Long accountId,
            String bucket,
            String objectKey,
            String fileName,
            String contentType,
            Long sizeBytes,
            String fileUrl,
            AttachmentUploadStatus uploadStatus
    ) {
        Attachment a = new Attachment();
        a.id = id;
        a.uuid = uuid;
        a.createdBy = createdBy;
        a.createdAt = createdAt;
        a.updatedBy = updatedBy;
        a.updatedAt = updatedAt;
        a.accountId = accountId;
        a.bucket = bucket;
        a.objectKey = objectKey;
        a.fileName = fileName;
        a.contentType = contentType;
        a.sizeBytes = sizeBytes;
        a.fileUrl = fileUrl;
        a.uploadStatus = uploadStatus;
        return a;
    }

    /**
     * Marks the attachment as successfully stored after upload confirmation.
     */
    public void markAvailable(Long updatedBy) {
        this.uploadStatus = AttachmentUploadStatus.AVAILABLE;
        this.updatedBy = updatedBy;
        this.updatedAt = Instant.now(this.clock);
    }

    /**
     * Marks the attachment as abandoned or timed out (used by maintenance sweep).
     */
    public void markExpired(Long updatedBy) {
        this.uploadStatus = AttachmentUploadStatus.EXPIRED;
        this.updatedBy = updatedBy;
        this.updatedAt = Instant.now(this.clock);
    }
}
