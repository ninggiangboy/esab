package dev.ngb.app.identity.application.usecase.password.forgot_password;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.usecase.password.forgot_password.dto.ForgotPasswordRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Always returns success to prevent email enumeration.
 */
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpSender otpSender;

    public void execute(ForgotPasswordRequest request) {
        log.info("Forgot password request (email masked for enumeration protection)");

        Optional<Account> accountOpt = accountRepository.findByEmail(request.email());
        if (accountOpt.isEmpty() || !accountOpt.get().isActive()) {
            log.debug("Forgot password: no active account for email, returning without sending OTP");
            return;
        }

        Account account = accountOpt.get();
        log.debug("Sending password reset OTP for accountId={}", account.getId());
        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.PASSWORD_RESET, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(request.email(), code, OtpPurpose.PASSWORD_RESET);
        log.info("Password reset OTP sent for accountId={}", account.getId());
    }
}
