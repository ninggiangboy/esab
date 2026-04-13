package dev.ngb.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FilenameUtilsTest {

    @Test
    void resolveAcceptsSimpleName() {
        Optional<FilenameUtils.ResolvedUploadFilename> r = FilenameUtils.resolveForAttachmentUpload(
                "  photo.png  ",
                FilenameUtils.DEFAULT_MAX_STORAGE_BASENAME_LENGTH
        );
        assertThat(r).isPresent();
        assertThat(r.get().displayName()).isEqualTo("photo.png");
        assertThat(r.get().storageBasename()).isEqualTo("photo.png");
    }

    @Test
    void resolveRejectsPathSegments() {
        assertThat(FilenameUtils.resolveForAttachmentUpload("../x", 180)).isEmpty();
        assertThat(FilenameUtils.resolveForAttachmentUpload("a/b", 180)).isEmpty();
        assertThat(FilenameUtils.resolveForAttachmentUpload("a\\b", 180)).isEmpty();
    }

    @Test
    void resolveRejectsBlank() {
        assertThat(FilenameUtils.resolveForAttachmentUpload(null, 180)).isEmpty();
        assertThat(FilenameUtils.resolveForAttachmentUpload("   ", 180)).isEmpty();
    }

    @Test
    void toSafeStorageBasenameSanitizesAndTruncates() {
        assertThat(FilenameUtils.toSafeStorageBasename("a b.txt", 100)).isEqualTo("a_b.txt");
        String longName = "x".repeat(200);
        assertThat(FilenameUtils.toSafeStorageBasename(longName, 10).length()).isEqualTo(10);
    }
}
