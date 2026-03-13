package dev.ngb.app.identity.application.usecase.reset_password;

import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.reset_password.dto.ResetPasswordRequest;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountStatus;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResetPasswordUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ResetPasswordUseCase useCase;

    @Test
    void shouldResetPasswordAndRevokeSessions() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "old-hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        AccountOtp otp = AccountOtp.create(1L, "123456", OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL);
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                1L, 2L, "token-hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        var request = new ResetPasswordRequest("user@test.com", "123456", "newPassword");

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.PASSWORD_RESET))
                .thenReturn(Optional.of(otp));
        when(passwordEncoder.encode("newPassword")).thenReturn("new-hash");
        when(accountSessionRepository.findActiveByAccountId(1L)).thenReturn(List.of(session));

        useCase.execute(request);

        verify(accountOtpRepository).save(otp);
        verify(accountRepository).save(account);
        verify(accountSessionRepository).saveAll(List.of(session));
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        var request = new ResetPasswordRequest("unknown@test.com", "123456", "newPassword");
        when(accountRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_FOUND);
    }

    @Test
    void shouldThrowWhenOtpNotFound() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new ResetPasswordRequest("user@test.com", "123456", "newPassword");

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.PASSWORD_RESET))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OTP);
    }

    @Test
    void shouldRevokeAllActiveSessionsAfterPasswordReset() {
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "old-hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        AccountOtp otp = AccountOtp.create(1L, "111111", OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL);
        AccountSession session1 = AccountSession.reconstruct(
                1L, "s1", null, Instant.now(), null, null,
                1L, 2L, "h1", "10.0.0.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        AccountSession session2 = AccountSession.reconstruct(
                2L, "s2", null, Instant.now(), null, null,
                1L, 3L, "h2", "10.0.0.2",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        var request = new ResetPasswordRequest("user@test.com", "111111", "newPw");

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(account));
        when(accountOtpRepository.findLatestActiveByAccountIdAndPurpose(1L, OtpPurpose.PASSWORD_RESET))
                .thenReturn(Optional.of(otp));
        when(passwordEncoder.encode("newPw")).thenReturn("new-hash");
        when(accountSessionRepository.findActiveByAccountId(1L)).thenReturn(List.of(session1, session2));

        useCase.execute(request);

        verify(accountSessionRepository).saveAll(argThat(sessions -> {
            List<AccountSession> list = (List<AccountSession>) sessions;
            return list.size() == 2
                    && list.stream().allMatch(s -> Boolean.TRUE.equals(s.getIsRevoked()));
        }));
    }
}
