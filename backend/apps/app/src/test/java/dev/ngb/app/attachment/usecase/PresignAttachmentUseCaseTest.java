package dev.ngb.app.attachment.usecase;

import dev.ngb.app.attachment.application.PresignAttachmentConstants;
import dev.ngb.app.attachment.application.port.PresignedPutObjectPort;
import dev.ngb.app.attachment.application.usecase.presign_attachment.PresignAttachmentUseCase;
import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentRequest;
import dev.ngb.app.attachment.application.usecase.presign_attachment.dto.PresignAttachmentResponse;
import dev.ngb.app.attachment.domain.AttachmentError;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.attachment.model.attachment.Attachment;
import dev.ngb.domain.attachment.repository.AttachmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PresignAttachmentUseCase")
class PresignAttachmentUseCaseTest {

    @Mock
    private PresignedPutObjectPort presignedPutObjectPort;

    @Mock
    private AttachmentRepository attachmentRepository;

    private PresignAttachmentUseCase useCase;

    @BeforeEach
    void setUp() {
        lenient().when(presignedPutObjectPort.storageBucket()).thenReturn("my-bucket");
        useCase = new PresignAttachmentUseCase(presignedPutObjectPort, attachmentRepository);
    }

    @Test
    void executePersistsAttachmentAndReturnsPresignedPayload() {
        when(presignedPutObjectPort.presignPut(any(PutObjectRequest.class), eq(Duration.ofSeconds(300))))
                .thenAnswer(inv -> {
                    PutObjectRequest req = inv.getArgument(0);
                    String key = req.key();
                    return new PresignedPutObjectPort.PresignedPutResult(
                            "https://example.com/presigned",
                            Map.of("Content-Type", "image/png"),
                            Instant.parse("2030-01-01T00:00:00Z"),
                            "https://my-bucket.s3.us-east-1.amazonaws.com/" + key,
                            "my-bucket"
                    );
                });

        ArgumentCaptor<Attachment> attachmentCaptor = ArgumentCaptor.forClass(Attachment.class);
        when(attachmentRepository.save(attachmentCaptor.capture())).thenAnswer(inv -> {
            Attachment a = inv.getArgument(0);
            return Attachment.reconstruct(
                    99L,
                    a.getUuid(),
                    a.getCreatedBy(),
                    a.getCreatedAt(),
                    a.getUpdatedBy(),
                    a.getUpdatedAt(),
                    a.getAccountId(),
                    a.getBucket(),
                    a.getObjectKey(),
                    a.getFileName(),
                    a.getContentType(),
                    a.getSizeBytes(),
                    a.getFileUrl(),
                    a.getUploadStatus()
            );
        });

        PresignAttachmentResponse response = useCase.execute(
                42L,
                new PresignAttachmentRequest("photo.png", "image/png", 512L)
        );

        assertThat(response.attachmentId()).isEqualTo(99L);
        assertThat(response.attachmentUuid()).isNotBlank();
        assertThat(response.uploadUrl()).isEqualTo("https://example.com/presigned");
        assertThat(response.uploadHeaders()).containsEntry("Content-Type", "image/png");
        assertThat(response.objectKey()).startsWith(PresignAttachmentConstants.OBJECT_KEY_PREFIX + "/42/");
        assertThat(response.objectKey()).endsWith("-photo.png");
        assertThat(response.fileUrl()).isEqualTo("https://my-bucket.s3.us-east-1.amazonaws.com/" + response.objectKey());
        assertThat(response.expiresAt()).isEqualTo(Instant.parse("2030-01-01T00:00:00Z"));

        Attachment savedInput = attachmentCaptor.getValue();
        assertThat(savedInput.getAccountId()).isEqualTo(42L);
        assertThat(savedInput.getBucket()).isEqualTo("my-bucket");
        assertThat(savedInput.getObjectKey()).isEqualTo(response.objectKey());
        assertThat(savedInput.getFileName()).isEqualTo("photo.png");
        assertThat(savedInput.getContentType()).isEqualTo("image/png");
        assertThat(savedInput.getSizeBytes()).isEqualTo(512L);
        assertThat(savedInput.getFileUrl()).isEqualTo(response.fileUrl());

        ArgumentCaptor<PutObjectRequest> putCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        ArgumentCaptor<Duration> ttlCaptor = ArgumentCaptor.forClass(Duration.class);
        verify(presignedPutObjectPort).presignPut(putCaptor.capture(), ttlCaptor.capture());
        assertThat(putCaptor.getValue().bucket()).isEqualTo("my-bucket");
        assertThat(putCaptor.getValue().key()).isEqualTo(response.objectKey());
        assertThat(putCaptor.getValue().contentType()).isEqualTo("image/png");
        assertThat(ttlCaptor.getValue()).isEqualTo(Duration.ofSeconds(300));
    }

    @Test
    void executeReturnsFileUrlFromPresignResult() {
        when(presignedPutObjectPort.presignPut(any(PutObjectRequest.class), any(Duration.class)))
                .thenAnswer(inv -> {
                    PutObjectRequest req = inv.getArgument(0);
                    return new PresignedPutObjectPort.PresignedPutResult(
                            "https://example.com/presigned",
                            Map.of(),
                            Instant.now(),
                            "https://cdn.example.com/assets/" + req.key(),
                            "my-bucket"
                    );
                });
        when(attachmentRepository.save(any(Attachment.class))).thenAnswer(inv -> {
            Attachment a = inv.getArgument(0);
            return Attachment.reconstruct(
                    1L,
                    a.getUuid(),
                    a.getCreatedBy(),
                    a.getCreatedAt(),
                    a.getUpdatedBy(),
                    a.getUpdatedAt(),
                    a.getAccountId(),
                    a.getBucket(),
                    a.getObjectKey(),
                    a.getFileName(),
                    a.getContentType(),
                    a.getSizeBytes(),
                    a.getFileUrl(),
                    a.getUploadStatus()
            );
        });

        PresignAttachmentResponse response = useCase.execute(
                1L,
                new PresignAttachmentRequest("doc.pdf", "application/pdf", 60_000L)
        );

        assertThat(response.fileUrl()).isEqualTo("https://cdn.example.com/assets/" + response.objectKey());
    }

    @Test
    void executeRejectsBlankFileName() {
        assertThatThrownBy(() -> useCase.execute(1L, new PresignAttachmentRequest(" ", "text/plain", null)))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.INVALID_FILE_NAME);
    }

    @Test
    void executeRejectsDisallowedContentType() {
        assertThatThrownBy(() -> useCase.execute(
                1L,
                new PresignAttachmentRequest("a.png", "image/jpeg", 512L)
        ))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.CONTENT_TYPE_NOT_ALLOWED);
    }

    @Test
    void executeRejectsOversizeWhenMaxConfigured() {
        assertThatThrownBy(() -> useCase.execute(
                1L,
                new PresignAttachmentRequest("a.bin", "application/pdf", 60_000_000L)
        ))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.FILE_TOO_LARGE);
    }

    @Test
    void executeRequiresSizeWhenMaxConfigured() {
        assertThatThrownBy(() -> useCase.execute(
                1L,
                new PresignAttachmentRequest("a.bin", "application/pdf", null)
        ))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.SIZE_DECLARATION_REQUIRED);
    }
}
