package dev.ngb.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeUtilsTest {

    @Test
    void emptyAllowlistAcceptsAny() {
        assertThat(ContentTypeUtils.isAllowedContentType("image/png", null)).isTrue();
        assertThat(ContentTypeUtils.isAllowedContentType("image/png", List.of())).isTrue();
    }

    @Test
    void allowlistMatchesCaseInsensitive() {
        List<String> allowed = List.of("image/png", "application/pdf");
        assertThat(ContentTypeUtils.isAllowedContentType("IMAGE/PNG", allowed)).isTrue();
        assertThat(ContentTypeUtils.isAllowedContentType("application/pdf", allowed)).isTrue();
        assertThat(ContentTypeUtils.isAllowedContentType("text/plain", allowed)).isFalse();
    }
}
