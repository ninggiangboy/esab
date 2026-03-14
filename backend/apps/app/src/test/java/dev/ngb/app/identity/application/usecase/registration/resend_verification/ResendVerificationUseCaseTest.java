package dev.ngb.app.identity.application.usecase.registration.resend_verification;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.usecase.registration.resend_verification.dto.ResendVerificationRequest;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResendVerificationUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private OtpCodeGenerator otpCodeGenerator;
    @Mock
    private OtpSender otpSender;

    @InjectMocks
    private ResendVerificationUseCase useCase;

    @Test
    void shouldResendVerificationOtpSuccessfully() {
        Account pendingAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.PENDING,
                false, false, false, null, null,
                new HashSet<>()
        );
        var request = new ResendVerificationRequest("user@test.com");

        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(pendingAccount));
        when(otpCodeGenerator.generate()).thenReturn("999888");

        useCase.execute(request);

        verify(accountOtpRepository).save(any());
        verify(otpSender).send(eq("user@test.com"), eq("999888"), eq(OtpPurpose.REGISTRATION));
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        var request = new ResendVerificationRequest("unknown@test.com");
        when(accountRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_FOUND);

        verify(otpSender, never()).send(any(), any(), any());
    }

    @Test
    void shouldThrowWhenEmailAlreadyVerified() {
        Account activeAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>()
        );
        var request = new ResendVerificationRequest("user@test.com");
        when(accountRepository.findByEmail("user@test.com")).thenReturn(Optional.of(activeAccount));

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.EMAIL_ALREADY_VERIFIED);

        verify(otpSender, never()).send(any(), any(), any());
    }
}
