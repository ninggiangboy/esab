package dev.ngb.app.identity.application.usecase.verify_email;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.verify_email.dto.VerifyEmailRequest;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountStatus;
import dev.ngb.domain.identity.model.account.DeviceType;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyEmailUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private VerifyEmailUseCase useCase;

    private static final String IP = "192.168.1.1";

    @Test
    void shouldVerifyEmailAndReturnTokens() {
        Account pendingAccount = buildPendingAccount();
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        var request = new VerifyEmailRequest("user@test.com", "123456",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-123"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(pendingAccount));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.REGISTRATION))
                .thenReturn(Optional.of(otp));
        when(accountRepository.save(any(Account.class))).thenReturn(pendingAccount);
        when(tokenProvider.generateRefreshToken()).thenReturn("refresh-token");
        when(tokenProvider.hashToken("refresh-token")).thenReturn("hashed");
        when(tokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access-token");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        AuthTokenResponse response = useCase.execute(request, IP);

        assertThat(response.accessToken()).isEqualTo("access-token");
        assertThat(response.refreshToken()).isEqualTo("refresh-token");
        assertThat(response.expiresIn()).isEqualTo(900L);
        verify(accountOtpRepository).save(otp);
        verify(accountSessionRepository).save(any());
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        var request = new VerifyEmailRequest("unknown@test.com", "123456",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));
        when(accountRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_FOUND);
    }

    @Test
    void shouldThrowWhenEmailAlreadyVerified() {
        Account activeAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new VerifyEmailRequest("user@test.com", "123456",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));
        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(activeAccount));

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.EMAIL_ALREADY_VERIFIED);
    }

    @Test
    void shouldThrowWhenOtpNotFound() {
        Account pendingAccount = buildPendingAccount();
        var request = new VerifyEmailRequest("user@test.com", "123456",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(pendingAccount));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.REGISTRATION))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);
    }

    private Account buildPendingAccount() {
        return Account.reconstruct(
                1L, "uuid-pending", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.PENDING,
                false, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
    }
}
