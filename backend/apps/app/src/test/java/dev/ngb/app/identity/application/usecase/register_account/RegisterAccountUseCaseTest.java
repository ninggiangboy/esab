package dev.ngb.app.identity.application.usecase.register_account;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.register_account.dto.RegisterAccountResponse;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountOtpRepository accountOtpRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OtpCodeGenerator otpCodeGenerator;
    @Mock
    private OtpSender otpSender;

    @InjectMocks
    private RegisterAccountUseCase useCase;

    @Test
    void shouldRegisterAccountSuccessfully() {
        var request = new RegisterAccountRequest("user@test.com", "password123");
        when(accountRepository.existsByEmail("user@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed-password");
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(otpCodeGenerator.generate()).thenReturn("123456");

        RegisterAccountResponse response = useCase.execute(request);

        assertThat(response.accountUuid()).isNotNull();
        verify(accountRepository).existsByEmail("user@test.com");
        verify(passwordEncoder).encode("password123");
        verify(accountRepository).save(any(Account.class));
        verify(accountOtpRepository).save(any());
        verify(otpSender).send(eq("user@test.com"), eq("123456"), eq(OtpPurpose.REGISTRATION));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        var request = new RegisterAccountRequest("existing@test.com", "password123");
        when(accountRepository.existsByEmail("existing@test.com")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.EMAIL_ALREADY_EXISTS);

        verify(accountRepository, never()).save(any());
        verify(otpSender, never()).send(any(), any(), any());
    }
}
