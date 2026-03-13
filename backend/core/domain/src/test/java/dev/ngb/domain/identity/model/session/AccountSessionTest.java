package dev.ngb.domain.identity.model.session;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class AccountSessionTest {

    @Test
    void shouldCreateSessionWithDefaultValues() {
        AccountSession session = AccountSession.create(1L, 2L, "token-hash", "192.168.1.1");

        assertThat(session.getAccountId()).isEqualTo(1L);
        assertThat(session.getDeviceId()).isEqualTo(2L);
        assertThat(session.getTokenHash()).isEqualTo("token-hash");
        assertThat(session.getIpAddress()).isEqualTo("192.168.1.1");
        assertThat(session.getIsRevoked()).isFalse();
        assertThat(session.getExpiresAt()).isNotNull();
        assertThat(session.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldSetExpirationTo30DaysFromCreation() {
        AccountSession session = AccountSession.create(1L, 2L, "hash", "10.0.0.1");

        Duration duration = Duration.between(session.getCreatedAt(), session.getExpiresAt());
        assertThat(duration).isBetween(Duration.ofDays(30).minusSeconds(1), Duration.ofDays(30).plusSeconds(1));
    }

    @Test
    void shouldRevokeSession() {
        AccountSession session = AccountSession.create(1L, 2L, "hash", "10.0.0.1");

        session.revoke();

        assertThat(session.getIsRevoked()).isTrue();
        assertThat(session.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldBeValidWhenNotRevokedAndNotExpired() {
        AccountSession session = AccountSession.create(1L, 2L, "hash", "10.0.0.1");

        assertThat(session.isValid()).isTrue();
    }

    @Test
    void shouldBeInvalidWhenRevoked() {
        AccountSession session = AccountSession.create(1L, 2L, "hash", "10.0.0.1");
        session.revoke();

        assertThat(session.isValid()).isFalse();
    }

    @Test
    void shouldBeInvalidWhenExpired() {
        AccountSession session = AccountSession.create(1L, 2L, "hash", "10.0.0.1");
        Instant futureTime = Instant.now().plus(Duration.ofDays(31));
        session.setClock(Clock.fixed(futureTime, ZoneOffset.UTC));

        assertThat(session.isValid()).isFalse();
    }

    @Test
    void shouldReconstructSessionWithAllFields() {
        Instant now = Instant.now();
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, now, null, now,
                10L, 20L, "hashed-token", "192.168.0.1",
                now.plus(Duration.ofDays(30)), false
        );

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getAccountId()).isEqualTo(10L);
        assertThat(session.getDeviceId()).isEqualTo(20L);
        assertThat(session.getTokenHash()).isEqualTo("hashed-token");
    }
}
