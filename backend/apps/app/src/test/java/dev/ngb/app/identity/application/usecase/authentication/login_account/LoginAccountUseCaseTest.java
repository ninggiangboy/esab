package dev.ngb.app.identity.application.usecase.authentication.login_account;

import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountRequest;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountResponse;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.auth.*;
import dev.ngb.domain.identity.model.auth.AccountDevice;
import dev.ngb.domain.identity.model.auth.DeviceType;
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
class LoginAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountDeviceRepository accountDeviceRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private AccountLoginHistoryRepository accountLoginHistoryRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private OtpCodeGenerator otpCodeGenerator;
    @Mock
    private OtpSender otpSender;

    @InjectMocks
    private LoginAccountUseCase useCase;

    private static final String IP = "192.168.1.1";

    @Test
    void shouldLoginSuccessfullyWithKnownDeviceAndNo2FA() {
        AccountDevice device = AccountDevice.reconstruct(
                1L, "dev-uuid", null, Instant.now(), null, null,
                1L, DeviceType.WEB, "Chrome", "fp-known",
                null, null, Instant.now(), true
        );
        Account account = buildActiveAccount(Set.of(device), false);
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-known"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(accountDeviceRepository.findByAccountIdAndFingerprint(1L, "fp-known")).thenReturn(Optional.of(device));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountDeviceRepository.save(any(AccountDevice.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tokenProvider.generateRefreshToken()).thenReturn("refresh-token");
        when(tokenProvider.hashToken("refresh-token")).thenReturn("hashed-refresh");
        when(tokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access-token");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        LoginAccountResponse response = useCase.execute(request, IP);

        assertThat(response.requiresVerification()).isFalse();
        assertThat(response.accessToken()).isEqualTo("access-token");
        assertThat(response.refreshToken()).isEqualTo("refresh-token");
        verify(accountSessionRepository).save(any());
        verify(accountLoginHistoryRepository).save(any());
    }

    @Test
    void shouldThrowWhenEmailNotFound() {
        var request = new LoginAccountRequest("unknown@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));
        when(accountRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_CREDENTIALS);
    }

    @Test
    void shouldThrowWhenPasswordIncorrect() {
        Account account = buildActiveAccount(new HashSet<>(), false);
        var request = new LoginAccountRequest("user@test.com", "wrong-password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("wrong-password", "hashed-pw")).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_CREDENTIALS);

        verify(accountLoginHistoryRepository).save(any());
    }

    @Test
    void shouldThrowWhenAccountPending() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hashed-pw", AccountStatus.PENDING,
                false, false, false, null, null
        );
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_PENDING);
    }

    @Test
    void shouldThrowWhenAccountSuspended() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hashed-pw", AccountStatus.SUSPENDED,
                true, false, false, null, null
        );
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_SUSPENDED);
    }

    @Test
    void shouldThrowWhenAccountBanned() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hashed-pw", AccountStatus.BANNED,
                true, false, false, null, null
        );
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_BANNED);
    }

    @Test
    void shouldRequireVerificationForNewDevice() {
        Account account = buildActiveAccount(new HashSet<>(), false);
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-new"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);
        when(accountDeviceRepository.save(any(AccountDevice.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(otpCodeGenerator.generate()).thenReturn("654321");
        when(tokenProvider.generateVerificationToken(any(), any())).thenReturn("ver-token");

        LoginAccountResponse response = useCase.execute(request, IP);

        assertThat(response.requiresVerification()).isTrue();
        assertThat(response.verificationToken()).isEqualTo("ver-token");
        assertThat(response.accessToken()).isNull();
        verify(otpSender).send(any(), any(), any());
    }

    @Test
    void shouldRequireVerificationWhen2FAEnabled() {
        AccountDevice device = AccountDevice.reconstruct(
                1L, "dev-uuid", null, Instant.now(), null, null,
                1L, DeviceType.WEB, "Chrome", "fp-known",
                null, null, Instant.now(), true
        );
        Account account = buildActiveAccount(Set.of(device), true);
        var request = new LoginAccountRequest("user@test.com", "password",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-known"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(accountDeviceRepository.findByAccountIdAndFingerprint(1L, "fp-known")).thenReturn(Optional.of(device));
        when(passwordEncoder.matches("password", "hashed-pw")).thenReturn(true);
        when(accountDeviceRepository.save(any(AccountDevice.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(otpCodeGenerator.generate()).thenReturn("111111");
        when(tokenProvider.generateVerificationToken(any(), any())).thenReturn("ver-token-2fa");

        LoginAccountResponse response = useCase.execute(request, IP);

        assertThat(response.requiresVerification()).isTrue();
        assertThat(response.verificationToken()).isEqualTo("ver-token-2fa");
    }

    private Account buildActiveAccount(Set<AccountDevice> devices, boolean twoFactorEnabled) {
        return Account.reconstruct(
                1L, "uuid-123", null, Instant.now(), null, null,
                "user@test.com", null, "hashed-pw", AccountStatus.ACTIVE,
                true, false, twoFactorEnabled, null, null
        );
    }
}
