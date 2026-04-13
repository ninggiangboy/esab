package dev.ngb.app.attachment.application.usecase.sweep_pending_attachments;

import dev.ngb.app.attachment.application.PresignAttachmentConstants;
import dev.ngb.app.attachment.application.port.AttachmentObjectStoragePort;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.attachment.model.AttachmentUploadStatus;
import dev.ngb.domain.attachment.model.attachment.Attachment;
import dev.ngb.domain.attachment.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

/**
 * Marks stale {@code PENDING_PUT} attachments as {@code AVAILABLE} when the object exists, or {@code EXPIRED} when it
 * does not, after presign TTL plus a grace window.
 */
@Slf4j
@RequiredArgsConstructor
public class SweepStalePendingAttachmentsUseCase implements UseCaseService {

    private static final long SYSTEM_ACTOR_ID = 0L;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentObjectStoragePort objectStoragePort;

    public void execute() {
        Instant cutoff = Instant.now().minusSeconds(
                PresignAttachmentConstants.PRESIGN_TTL_SECONDS
                        + PresignAttachmentConstants.PENDING_STALE_GRACE_SECONDS
        );
        List<Attachment> stale = attachmentRepository.findByUploadStatusAndCreatedAtBefore(
                AttachmentUploadStatus.PENDING_PUT,
                cutoff
        );
        for (Attachment attachment : stale) {
            try {
                if (objectStoragePort.objectExists(attachment.getBucket(), attachment.getObjectKey())) {
                    attachment.markAvailable(SYSTEM_ACTOR_ID);
                } else {
                    attachment.markExpired(SYSTEM_ACTOR_ID);
                }
                attachmentRepository.save(attachment);
            } catch (RuntimeException ex) {
                log.warn(
                        "Pending attachment sweep failed attachmentId={} uuid={}",
                        attachment.getId(),
                        attachment.getUuid(),
                        ex
                );
            }
        }
    }
}
