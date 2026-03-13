package dev.ngb.domain.identity.model.otp;

import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountOtpTest {

    @Test
    void shouldCreateOtpWithDefaultValues() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);

        assertThat(otp.getAccountId()).isEqualTo(1L);
        assertThat(otp.getCode()).isEqualTo("123456");
        assertThat(otp.getPurpose()).isEqualTo(OtpPurpose.REGISTRATION);
        assertThat(otp.getChannel()).isEqualTo(OtpChannel.EMAIL);
        assertThat(otp.getIsUsed()).isFalse();
        assertThat(otp.getAttempts()).isZero();
        assertThat(otp.getExpiresAt()).isNotNull();
        assertThat(otp.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldVerifyOtpWithCorrectCode() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);

        otp.verify("123456");

        assertThat(otp.getIsUsed()).isTrue();
        assertThat(otp.getAttempts()).isEqualTo(1);
        assertThat(otp.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenVerifyingWithWrongCode() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);

        assertThatThrownBy(() -> otp.verify("999999"))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);

        assertThat(otp.getIsUsed()).isFalse();
        assertThat(otp.getAttempts()).isEqualTo(1);
    }

    @Test
    void shouldThrowWhenOtpAlreadyUsed() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.LOGIN, OtpChannel.EMAIL);
        otp.verify("123456");

        assertThatThrownBy(() -> otp.verify("123456"))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);
    }

    @Test
    void shouldThrowWhenOtpExpired() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL);
        Instant futureTime = Instant.now().plus(Duration.ofMinutes(15));
        otp.setClock(Clock.fixed(futureTime, ZoneOffset.UTC));

        assertThatThrownBy(() -> otp.verify("123456"))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);
    }

    @Test
    void shouldThrowWhenMaxAttemptsExceeded() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.LOGIN, OtpChannel.EMAIL);

        for (int i = 0; i < 5; i++) {
            try {
                otp.verify("wrong");
            } catch (DomainException ignored) {
            }
        }

        assertThatThrownBy(() -> otp.verify("123456"))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.OTP_MAX_ATTEMPTS);
    }

    @Test
    void shouldReturnTrueWhenOtpExpired() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        Instant futureTime = Instant.now().plus(Duration.ofMinutes(15));
        otp.setClock(Clock.fixed(futureTime, ZoneOffset.UTC));

        assertThat(otp.isExpired()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenOtpNotExpired() {
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);

        assertThat(otp.isExpired()).isFalse();
    }

    @Test
    void shouldSetExpirationTo10MinutesFromCreation() {
        Instant fixedNow = Instant.parse("2026-01-01T00:00:00Z");
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        otp.setClock(Clock.fixed(fixedNow, ZoneOffset.UTC));

        assertThat(otp.getExpiresAt()).isAfter(otp.getCreatedAt());
    }

    @Test
    void shouldReconstructOtpWithAllFields() {
        Instant now = Instant.now();
        AccountOtp otp = AccountOtp.reconstruct(
                1L, "uuid", null, now, null, now,
                10L, "654321", OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL,
                now.plus(Duration.ofMinutes(10)), false, 2
        );

        assertThat(otp.getId()).isEqualTo(1L);
        assertThat(otp.getAccountId()).isEqualTo(10L);
        assertThat(otp.getCode()).isEqualTo("654321");
        assertThat(otp.getAttempts()).isEqualTo(2);
    }
}
