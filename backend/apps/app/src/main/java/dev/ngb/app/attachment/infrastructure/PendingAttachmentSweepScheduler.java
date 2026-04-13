package dev.ngb.app.attachment.infrastructure;

import dev.ngb.app.attachment.application.usecase.sweep_pending_attachments.SweepStalePendingAttachmentsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.attachment.sweep", name = "enabled", havingValue = "true")
@ConditionalOnBean(SweepStalePendingAttachmentsUseCase.class)
public class PendingAttachmentSweepScheduler {

    private final SweepStalePendingAttachmentsUseCase sweepStalePendingAttachmentsUseCase;

    @Scheduled(fixedDelayString = "${app.attachment.sweep.fixed-delay-ms:600000}")
    public void sweepStalePendingAttachments() {
        sweepStalePendingAttachmentsUseCase.execute();
    }
}
