package dev.ngb.app.identity.application.usecase.authentication.verify_login;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.authentication.verify_login.dto.VerifyLoginRequest;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.account.AccountStatus;
import dev.ngb.domain.identity.model.account.DeviceType;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountDeviceRepository;
import dev.ngb.domain.identity.repository.AccountLoginHistoryRepository;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyLoginUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountDeviceRepository accountDeviceRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private AccountLoginHistoryRepository accountLoginHistoryRepository;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private VerifyLoginUseCase useCase;

    private static final String IP = "192.168.1.1";

    @Test
    void shouldVerifyLoginAndReturnTokens() {
        AccountDevice device = AccountDevice.reconstruct(
                5L, "dev-uuid", null, Instant.now(), null, null,
                1L, DeviceType.WEB, "Chrome", "fp",
                null, null, Instant.now(), false
        );
        Account account = buildActiveAccountWithDevice(device);
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.LOGIN, OtpChannel.EMAIL);
        var request = new VerifyLoginRequest("ver-token", "123456");

        when(tokenProvider.parseVerificationToken("ver-token"))
                .thenReturn(new TokenProvider.VerificationClaims(1L, 5L));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.LOGIN))
                .thenReturn(Optional.of(otp));
        when(accountDeviceRepository.findById(5L)).thenReturn(Optional.of(device));
        when(tokenProvider.generateRefreshToken()).thenReturn("refresh");
        when(tokenProvider.hashToken("refresh")).thenReturn("hashed");
        when(tokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        AuthTokenResponse response = useCase.execute(request, IP);

        assertThat(response.accessToken()).isEqualTo("access");
        assertThat(response.refreshToken()).isEqualTo("refresh");
        verify(accountSessionRepository).save(any());
        verify(accountLoginHistoryRepository).save(any());
    }

    @Test
    void shouldThrowWhenVerificationTokenInvalid() {
        var request = new VerifyLoginRequest("bad-token", "123456");
        when(tokenProvider.parseVerificationToken("bad-token")).thenThrow(new RuntimeException("Invalid"));

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_VERIFICATION_TOKEN);
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        var request = new VerifyLoginRequest("ver-token", "123456");
        when(tokenProvider.parseVerificationToken("ver-token"))
                .thenReturn(new TokenProvider.VerificationClaims(999L, 5L));
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_FOUND);
    }

    @Test
    void shouldThrowWhenOtpNotFound() {
        AccountDevice device = AccountDevice.reconstruct(
                5L, "dev-uuid", null, Instant.now(), null, null,
                1L, DeviceType.WEB, "Chrome", "fp",
                null, null, Instant.now(), false
        );
        Account account = buildActiveAccountWithDevice(device);
        var request = new VerifyLoginRequest("ver-token", "123456");

        when(tokenProvider.parseVerificationToken("ver-token"))
                .thenReturn(new TokenProvider.VerificationClaims(1L, 5L));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.LOGIN))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);
    }

    @Test
    void shouldThrowWhenDeviceNotFoundInAccount() {
        Account account = buildActiveAccountWithDevice(null);
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.LOGIN, OtpChannel.EMAIL);
        var request = new VerifyLoginRequest("ver-token", "123456");

        when(tokenProvider.parseVerificationToken("ver-token"))
                .thenReturn(new TokenProvider.VerificationClaims(1L, 999L));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.LOGIN))
                .thenReturn(Optional.of(otp));
        when(accountDeviceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_VERIFICATION_TOKEN);
    }

    private Account buildActiveAccountWithDevice(AccountDevice device) {
        return Account.reconstruct(
                1L, "uuid-123", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, true, null, null,
                new HashSet<>(), new HashSet<>(Set.of(device))
        );
    }
}
