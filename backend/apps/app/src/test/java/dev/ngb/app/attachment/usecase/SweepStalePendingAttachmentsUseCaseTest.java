package dev.ngb.app.attachment.usecase;

import dev.ngb.app.attachment.application.port.AttachmentObjectStoragePort;
import dev.ngb.app.attachment.application.usecase.sweep_pending_attachments.SweepStalePendingAttachmentsUseCase;
import dev.ngb.domain.attachment.model.AttachmentUploadStatus;
import dev.ngb.domain.attachment.model.attachment.Attachment;
import dev.ngb.domain.attachment.repository.AttachmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SweepStalePendingAttachmentsUseCase")
class SweepStalePendingAttachmentsUseCaseTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private AttachmentObjectStoragePort attachmentObjectStoragePort;

    private SweepStalePendingAttachmentsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SweepStalePendingAttachmentsUseCase(attachmentRepository, attachmentObjectStoragePort);
    }

    @Test
    void executeDoesNothingWhenNoStaleRows() {
        when(attachmentRepository.findByUploadStatusAndCreatedAtBefore(eq(AttachmentUploadStatus.PENDING_PUT), any()))
                .thenReturn(List.of());

        useCase.execute();

        verify(attachmentRepository).findByUploadStatusAndCreatedAtBefore(eq(AttachmentUploadStatus.PENDING_PUT), any());
        verifyNoInteractions(attachmentObjectStoragePort);
    }

    @Test
    void executeMarksAvailableWhenObjectExists() {
        Attachment stale = stalePending("uuid-a", "bucket-a", "key-a");
        when(attachmentRepository.findByUploadStatusAndCreatedAtBefore(eq(AttachmentUploadStatus.PENDING_PUT), any()))
                .thenReturn(List.of(stale));
        when(attachmentObjectStoragePort.objectExists("bucket-a", "key-a")).thenReturn(true);

        useCase.execute();

        ArgumentCaptor<Attachment> captor = ArgumentCaptor.forClass(Attachment.class);
        verify(attachmentRepository).save(captor.capture());
        assertThat(captor.getValue().getUploadStatus()).isEqualTo(AttachmentUploadStatus.AVAILABLE);
        assertThat(captor.getValue().getUpdatedBy()).isEqualTo(0L);
    }

    @Test
    void executeMarksExpiredWhenObjectMissing() {
        Attachment stale = stalePending("uuid-b", "bucket-b", "key-b");
        when(attachmentRepository.findByUploadStatusAndCreatedAtBefore(eq(AttachmentUploadStatus.PENDING_PUT), any()))
                .thenReturn(List.of(stale));
        when(attachmentObjectStoragePort.objectExists("bucket-b", "key-b")).thenReturn(false);

        useCase.execute();

        ArgumentCaptor<Attachment> captor = ArgumentCaptor.forClass(Attachment.class);
        verify(attachmentRepository).save(captor.capture());
        assertThat(captor.getValue().getUploadStatus()).isEqualTo(AttachmentUploadStatus.EXPIRED);
        assertThat(captor.getValue().getUpdatedBy()).isEqualTo(0L);
    }

    @Test
    void executeSkipsSaveWhenHeadFails() {
        Attachment stale = stalePending("uuid-c", "bucket-c", "key-c");
        when(attachmentRepository.findByUploadStatusAndCreatedAtBefore(eq(AttachmentUploadStatus.PENDING_PUT), any()))
                .thenReturn(List.of(stale));
        when(attachmentObjectStoragePort.objectExists("bucket-c", "key-c")).thenThrow(new RuntimeException("s3 down"));

        useCase.execute();

        verify(attachmentRepository, never()).save(any());
    }

    private static Attachment stalePending(String uuid, String bucket, String key) {
        return Attachment.reconstruct(
                1L,
                uuid,
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                99L,
                bucket,
                key,
                "f",
                "image/png",
                1L,
                "https://x",
                AttachmentUploadStatus.PENDING_PUT
        );
    }
}
