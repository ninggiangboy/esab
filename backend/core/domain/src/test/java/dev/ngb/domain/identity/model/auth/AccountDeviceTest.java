package dev.ngb.domain.identity.model.auth;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDeviceTest {

    @Test
    void shouldCreateDeviceWithDefaultValues() {
        AccountDevice device = AccountDevice.create(1L, DeviceType.WEB, "Chrome", "fp-123");

        assertThat(device.getAccountId()).isEqualTo(1L);
        assertThat(device.getDeviceType()).isEqualTo(DeviceType.WEB);
        assertThat(device.getDeviceName()).isEqualTo("Chrome");
        assertThat(device.getFingerprint()).isEqualTo("fp-123");
        assertThat(device.getIsTrusted()).isFalse();
        assertThat(device.getLastActiveAt()).isNotNull();
        assertThat(device.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldMarkDeviceAsTrusted() {
        AccountDevice device = AccountDevice.create(1L, DeviceType.IOS, "iPhone", "fp-456");

        device.markTrusted();

        assertThat(device.getIsTrusted()).isTrue();
        assertThat(device.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateLastActiveAtOnTouch() {
        AccountDevice device = AccountDevice.create(1L, DeviceType.ANDROID, "Pixel", "fp-789");
        Instant fixedTime = Instant.parse("2026-01-01T12:00:00Z");
        device.setClock(Clock.fixed(fixedTime, ZoneOffset.UTC));

        device.touch();

        assertThat(device.getLastActiveAt()).isEqualTo(fixedTime);
        assertThat(device.getUpdatedAt()).isEqualTo(fixedTime);
    }

    @Test
    void shouldReconstructDeviceWithAllFields() {
        Instant now = Instant.now();
        AccountDevice device = AccountDevice.reconstruct(
                1L, "uuid", null, now, null, now,
                10L, DeviceType.DESKTOP, "Firefox", "fp-abc",
                "Mozilla/5.0", "push-token-123", now, true
        );

        assertThat(device.getId()).isEqualTo(1L);
        assertThat(device.getAccountId()).isEqualTo(10L);
        assertThat(device.getUserAgent()).isEqualTo("Mozilla/5.0");
        assertThat(device.getPushToken()).isEqualTo("push-token-123");
        assertThat(device.getIsTrusted()).isTrue();
    }
}
