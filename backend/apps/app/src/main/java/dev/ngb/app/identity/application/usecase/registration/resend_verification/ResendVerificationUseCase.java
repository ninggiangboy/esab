package dev.ngb.app.identity.application.usecase.registration.resend_verification;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.usecase.registration.resend_verification.dto.ResendVerificationRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResendVerificationUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpSender otpSender;

    public void execute(ResendVerificationRequest request) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(AccountError.ACCOUNT_NOT_FOUND::exception);

        if (!account.isPending()) {
            throw AccountError.EMAIL_ALREADY_VERIFIED.exception();
        }

        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(request.email(), code, OtpPurpose.REGISTRATION);
    }
}
