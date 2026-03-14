package dev.ngb.app.identity.application.usecase.registration.register_account;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RegisterAccountUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpSender otpSender;

    public RegisterAccountResponse execute(RegisterAccountRequest request) {
        log.info("Register account attempt for email={}", request.email() != null ? request.email().replaceAll("(?<=.).(?=.*@)", "*") : "***");

        if (accountRepository.existsByEmail(request.email())) {
            log.warn("Register failed: email already exists");
            throw AccountError.EMAIL_ALREADY_EXISTS.exception();
        }

        String passwordHash = passwordEncoder.encode(request.password());
        Account account = Account.create(request.email(), passwordHash);
        account = accountRepository.save(account);
        log.debug("Account created accountId={}", account.getId());

        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(request.email(), code, OtpPurpose.REGISTRATION);
        log.debug("Registration OTP sent for accountId={}", account.getId());

        log.info("Register account successful accountId={}, accountUuid={}", account.getId(), account.getUuid());
        return new RegisterAccountResponse(account.getUuid());
    }
}
