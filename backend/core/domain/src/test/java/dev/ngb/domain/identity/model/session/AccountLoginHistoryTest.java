package dev.ngb.domain.identity.model.session;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class AccountLoginHistoryTest {

    @Test
    void shouldCreateSuccessfulLoginHistory() {
        AccountLoginHistory history = AccountLoginHistory.create(
                1L, 2L, "192.168.1.1", "Mozilla/5.0", LoginResult.SUCCESS, null
        );

        assertThat(history.getAccountId()).isEqualTo(1L);
        assertThat(history.getDeviceId()).isEqualTo(2L);
        assertThat(history.getIpAddress()).isEqualTo("192.168.1.1");
        assertThat(history.getUserAgent()).isEqualTo("Mozilla/5.0");
        assertThat(history.getResult()).isEqualTo(LoginResult.SUCCESS);
        assertThat(history.getFailureReason()).isNull();
        assertThat(history.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldCreateFailedLoginHistory() {
        AccountLoginHistory history = AccountLoginHistory.create(
                1L, null, "10.0.0.1", null, LoginResult.FAILED, "Invalid password"
        );

        assertThat(history.getResult()).isEqualTo(LoginResult.FAILED);
        assertThat(history.getFailureReason()).isEqualTo("Invalid password");
        assertThat(history.getDeviceId()).isNull();
    }

    @Test
    void shouldReconstructLoginHistoryWithAllFields() {
        Instant now = Instant.now();
        AccountLoginHistory history = AccountLoginHistory.reconstruct(
                1L, "uuid", null, now, null, now,
                10L, 20L, "172.16.0.1", "Safari/17", LoginResult.BLOCKED, "Too many attempts"
        );

        assertThat(history.getId()).isEqualTo(1L);
        assertThat(history.getAccountId()).isEqualTo(10L);
        assertThat(history.getResult()).isEqualTo(LoginResult.BLOCKED);
    }
}
