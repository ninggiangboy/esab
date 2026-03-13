package dev.ngb.app.identity.application.usecase.password.forgot_password;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.usecase.password.forgot_password.dto.ForgotPasswordRequest;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountStatus;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private OtpCodeGenerator otpCodeGenerator;
    @Mock
    private OtpSender otpSender;

    @InjectMocks
    private ForgotPasswordUseCase useCase;

    @Test
    void shouldSendResetOtpForActiveAccount() {
        Account activeAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new ForgotPasswordRequest("user@test.com");

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(activeAccount));
        when(otpCodeGenerator.generate()).thenReturn("112233");

        useCase.execute(request);

        verify(accountOtpRepository).save(any());
        verify(otpSender).send(eq("user@test.com"), eq("112233"), eq(OtpPurpose.PASSWORD_RESET));
    }

    @Test
    void shouldDoNothingWhenAccountNotFound() {
        var request = new ForgotPasswordRequest("unknown@test.com");
        when(accountRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        useCase.execute(request);

        verify(otpSender, never()).send(any(), any(), any());
        verify(accountOtpRepository, never()).save(any());
    }

    @Test
    void shouldDoNothingWhenAccountNotActive() {
        Account pendingAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.PENDING,
                false, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new ForgotPasswordRequest("user@test.com");
        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(pendingAccount));

        useCase.execute(request);

        verify(otpSender, never()).send(any(), any(), any());
        verify(accountOtpRepository, never()).save(any());
    }
}
