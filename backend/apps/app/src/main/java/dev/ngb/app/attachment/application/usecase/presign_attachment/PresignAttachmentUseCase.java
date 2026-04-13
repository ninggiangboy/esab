package dev.ngb.app.attachment.application.usecase.presign_attachment;

import dev.ngb.app.attachment.application.PresignAttachmentConstants;
import dev.ngb.app.attachment.application.port.PresignedPutObjectPort;
import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentRequest;
import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentResponse;
import dev.ngb.app.attachment.domain.AttachmentError;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.attachment.model.attachment.Attachment;
import dev.ngb.domain.attachment.repository.AttachmentRepository;
import dev.ngb.util.ContentTypeUtils;
import dev.ngb.util.FilenameUtils;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public class PresignAttachmentUseCase implements UseCaseService {

    private final PresignedPutObjectPort presignedPutObjectPort;
    private final AttachmentRepository attachmentRepository;

    public PresignAttachmentResponse execute(Long accountId, PresignAttachmentRequest request) {
        FilenameUtils.ResolvedUploadFilename names = FilenameUtils
                .resolveForAttachmentUpload(request.fileName(), FilenameUtils.DEFAULT_MAX_STORAGE_BASENAME_LENGTH)
                .orElseThrow(AttachmentError.INVALID_FILE_NAME::exception);

        String contentType = request.contentType() == null ? "" : request.contentType().trim();
        if (contentType.isEmpty()) {
            throw AttachmentError.CONTENT_TYPE_REQUIRED.exception();
        }
        if (!ContentTypeUtils.isAllowedContentType(contentType, PresignAttachmentConstants.ALLOWED_CONTENT_TYPES)) {
            throw AttachmentError.CONTENT_TYPE_NOT_ALLOWED.exception();
        }

        Long sizeBytes = request.sizeBytes();
        if (PresignAttachmentConstants.MAX_ATTACHMENT_SIZE_BYTES > 0) {
            if (sizeBytes == null || sizeBytes <= 0) {
                throw AttachmentError.SIZE_DECLARATION_REQUIRED.exception();
            }
            if (sizeBytes > PresignAttachmentConstants.MAX_ATTACHMENT_SIZE_BYTES) {
                throw AttachmentError.FILE_TOO_LARGE.exception();
            }
        }

        String objectKey = PresignAttachmentConstants.OBJECT_KEY_PREFIX + "/" + accountId + "/"
                + UUID.randomUUID() + "-" + names.storageBasename();

        PutObjectRequest putObject = PutObjectRequest.builder()
                .bucket(presignedPutObjectPort.storageBucket())
                .key(objectKey)
                .contentType(contentType)
                .build();

        Duration ttl = Duration.ofSeconds(Math.max(
                PresignAttachmentConstants.MIN_PRESIGN_TTL_SECONDS,
                PresignAttachmentConstants.PRESIGN_TTL_SECONDS
        ));
        PresignedPutObjectPort.PresignedPutResult signed = presignedPutObjectPort.presignPut(putObject, ttl);

        Attachment pending = Attachment.createPendingPut(
                accountId,
                signed.bucket(),
                objectKey,
                names.displayName(),
                contentType,
                sizeBytes,
                signed.fileUrl(),
                accountId
        );
        Attachment saved = attachmentRepository.save(pending);

        return new PresignAttachmentResponse(
                saved.getId(),
                saved.getUuid(),
                signed.uploadUrl(),
                signed.uploadHeaders(),
                objectKey,
                signed.fileUrl(),
                signed.expiresAt()
        );
    }
}
