package dev.ngb.app.identity.application.usecase.forgot_password;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.usecase.forgot_password.dto.ForgotPasswordRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Always returns success to prevent email enumeration.
 */
@RequiredArgsConstructor
public class ForgotPasswordUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpSender otpSender;

    public void execute(ForgotPasswordRequest request) {
        Optional<Account> accountOpt = accountRepository.findByEmail(request.email());
        if (accountOpt.isEmpty() || !accountOpt.get().isActive()) {
            return;
        }

        Account account = accountOpt.get();
        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(request.email(), code, OtpPurpose.PASSWORD_RESET);
    }
}
