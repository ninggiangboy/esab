package dev.ngb.app.attachment.usecase;

import dev.ngb.app.attachment.application.port.AttachmentObjectStoragePort;
import dev.ngb.app.attachment.application.usecase.complete_attachment.CompleteAttachmentUseCase;
import dev.ngb.app.attachment.domain.AttachmentError;
import dev.ngb.domain.DomainException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CompleteAttachmentUseCase")
class CompleteAttachmentUseCaseTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private AttachmentObjectStoragePort attachmentObjectStoragePort;

    private CompleteAttachmentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CompleteAttachmentUseCase(attachmentRepository, attachmentObjectStoragePort);
    }

    @Test
    void executeMarksAvailableWhenObjectExists() {
        Attachment pending = pendingAttachment();
        when(attachmentRepository.findByUuidAndAccountId("uuid-1", 10L)).thenReturn(Optional.of(pending));
        when(attachmentObjectStoragePort.objectExists("bucket-a", "key-1")).thenReturn(true);

        useCase.execute(10L, "uuid-1");

        ArgumentCaptor<Attachment> captor = ArgumentCaptor.forClass(Attachment.class);
        verify(attachmentRepository).save(captor.capture());
        assertThat(captor.getValue().getUploadStatus()).isEqualTo(AttachmentUploadStatus.AVAILABLE);
        assertThat(captor.getValue().getUpdatedBy()).isEqualTo(10L);
    }

    @Test
    void executeThrowsWhenAttachmentMissing() {
        when(attachmentRepository.findByUuidAndAccountId("missing", 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(1L, "missing"))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.ATTACHMENT_NOT_FOUND);
    }

    @Test
    void executeThrowsWhenNotPending() {
        Attachment available = Attachment.reconstruct(
                1L,
                "uuid-1",
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                10L,
                "bucket-a",
                "key-1",
                "f",
                "image/png",
                1L,
                "https://x",
                AttachmentUploadStatus.AVAILABLE
        );
        when(attachmentRepository.findByUuidAndAccountId("uuid-1", 10L)).thenReturn(Optional.of(available));

        assertThatThrownBy(() -> useCase.execute(10L, "uuid-1"))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.ATTACHMENT_NOT_PENDING);
    }

    @Test
    void executeThrowsWhenObjectMissingInStorage() {
        Attachment pending = pendingAttachment();
        when(attachmentRepository.findByUuidAndAccountId("uuid-1", 10L)).thenReturn(Optional.of(pending));
        when(attachmentObjectStoragePort.objectExists("bucket-a", "key-1")).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(10L, "uuid-1"))
                .isInstanceOf(DomainException.class)
                .extracting(ex -> ((DomainException) ex).getError())
                .isEqualTo(AttachmentError.ATTACHMENT_OBJECT_NOT_IN_STORAGE);

        verify(attachmentRepository).findByUuidAndAccountId("uuid-1", 10L);
        verify(attachmentObjectStoragePort).objectExists("bucket-a", "key-1");
        verify(attachmentRepository, never()).save(any());
    }

    private static Attachment pendingAttachment() {
        return Attachment.reconstruct(
                1L,
                "uuid-1",
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                1L,
                Instant.parse("2020-01-01T00:00:00Z"),
                10L,
                "bucket-a",
                "key-1",
                "f",
                "image/png",
                1L,
                "https://x",
                AttachmentUploadStatus.PENDING_PUT
        );
    }
}
