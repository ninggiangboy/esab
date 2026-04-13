package dev.ngb.app.attachment.infrastructure;

import dev.ngb.app.attachment.application.port.AttachmentObjectStoragePort;
import dev.ngb.app.attachment.application.port.PresignedPutObjectPort;
import dev.ngb.app.attachment.application.usecase.complete_attachment.CompleteAttachmentUseCase;
import dev.ngb.app.attachment.application.usecase.presign_attachment.PresignAttachmentUseCase;
import dev.ngb.app.attachment.application.usecase.sweep_pending_attachments.SweepStalePendingAttachmentsUseCase;
import dev.ngb.domain.attachment.repository.AttachmentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(S3StorageProperties.class)
public class S3StorageConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app.storage.s3", name = "bucket")
    public S3Presigner s3Presigner(S3StorageProperties properties) {
        var builder = S3Presigner.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create());
        if (properties.getEndpoint() != null && !properties.getEndpoint().isBlank()) {
            builder.endpointOverride(URI.create(properties.getEndpoint().trim()));
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.storage.s3", name = "bucket")
    public S3Client s3Client(S3StorageProperties properties) {
        var builder = S3Client.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create());
        if (properties.getEndpoint() != null && !properties.getEndpoint().isBlank()) {
            builder.endpointOverride(URI.create(properties.getEndpoint().trim()));
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnBean(S3Presigner.class)
    public PresignedPutObjectPort presignedPutObjectPort(S3Presigner s3Presigner, S3StorageProperties properties) {
        return new AwsS3PresignedPutAdapter(s3Presigner, properties);
    }

    @Bean
    @ConditionalOnBean(PresignedPutObjectPort.class)
    public PresignAttachmentUseCase presignAttachmentUseCase(
            PresignedPutObjectPort presignedPutObjectPort,
            AttachmentRepository attachmentRepository
    ) {
        return new PresignAttachmentUseCase(presignedPutObjectPort, attachmentRepository);
    }

    @Bean
    @ConditionalOnBean(S3Client.class)
    public AttachmentObjectStoragePort attachmentObjectStoragePort(S3Client s3Client) {
        return new AwsS3AttachmentObjectStorageAdapter(s3Client);
    }

    @Bean
    @ConditionalOnBean(AttachmentObjectStoragePort.class)
    public CompleteAttachmentUseCase completeAttachmentUseCase(
            AttachmentRepository attachmentRepository,
            AttachmentObjectStoragePort attachmentObjectStoragePort
    ) {
        return new CompleteAttachmentUseCase(attachmentRepository, attachmentObjectStoragePort);
    }

    @Bean
    @ConditionalOnBean(AttachmentObjectStoragePort.class)
    public SweepStalePendingAttachmentsUseCase sweepStalePendingAttachmentsUseCase(
            AttachmentRepository attachmentRepository,
            AttachmentObjectStoragePort attachmentObjectStoragePort
    ) {
        return new SweepStalePendingAttachmentsUseCase(attachmentRepository, attachmentObjectStoragePort);
    }
}
