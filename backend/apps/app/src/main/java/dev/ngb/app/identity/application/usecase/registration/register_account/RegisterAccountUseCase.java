package dev.ngb.app.identity.application.usecase.registration.register_account;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.auth.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Registers a new account with email and password. The account stays pending until the user
 * completes email verification. Passwords are hashed through PasswordEncoder before persistence;
 * plaintext is never stored.
 *
 * Duplicate emails are rejected with a domain error. After the account is saved, a registration
 * OTP is generated, stored for later verification, and sent via OtpSender. The response exposes
 * only the public account UUID, not the internal database id.
 */
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

        // One account per email; duplicates are a hard business rule violation.
        if (accountRepository.existsByEmail(request.email())) {
            log.warn("Register failed: email already exists");
            throw AccountError.EMAIL_ALREADY_EXISTS.exception();
        }

        // Hash before persistence; domain factory builds a pending account until email is verified.
        String passwordHash = passwordEncoder.encode(request.password());
        Account account = Account.create(request.email(), passwordHash);
        account = accountRepository.save(account);
        log.debug("Account created accountId={}", account.getId());

        // Persist OTP so VerifyEmail can validate the same code path and purpose.
        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.REGISTRATION, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        // Delivery is a port so SMTP vs queue stays out of the domain.
        otpSender.send(request.email(), code, OtpPurpose.REGISTRATION);
        log.debug("Registration OTP sent for accountId={}", account.getId());

        log.info("Register account successful accountId={}, accountUuid={}", account.getId(), account.getUuid());
        return new RegisterAccountResponse(account.getUuid());
    }
}
