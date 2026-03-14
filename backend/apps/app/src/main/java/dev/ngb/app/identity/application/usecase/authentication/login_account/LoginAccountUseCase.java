package dev.ngb.app.identity.application.usecase.authentication.login_account;

import dev.ngb.app.identity.application.port.OtpCodeGenerator;
import dev.ngb.app.identity.application.port.OtpSender;
import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountRequest;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpChannel;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.model.session.AccountLoginHistory;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.model.session.LoginResult;
import dev.ngb.domain.identity.repository.AccountLoginHistoryRepository;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginAccountUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountLoginHistoryRepository accountLoginHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final OtpCodeGenerator otpCodeGenerator;
    private final OtpSender otpSender;

    public LoginAccountResponse execute(LoginAccountRequest request, String ipAddress) {
        log.info("Login attempt for email={}", maskEmail(request.email()));

        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Login failed: account not found for email={}", maskEmail(request.email()));
                    return AccountError.INVALID_CREDENTIALS.exception();
                });

        if (!passwordEncoder.matches(request.password(), account.getPasswordHash())) {
            log.warn("Login failed: invalid password for accountId={}", account.getId());
            accountLoginHistoryRepository.save(
                    AccountLoginHistory.createFailure(account.getId(), null, ipAddress, null, "Invalid password")
            );
            throw AccountError.INVALID_CREDENTIALS.exception();
        }

        log.debug("Password verified for accountId={}", account.getId());
        validateAccountStatus(account);

        String fingerprint = request.deviceInfo().fingerprint();
        AccountDevice existingDevice = account.findDeviceByFingerprint(fingerprint).orElse(null);

        boolean isNewDevice = existingDevice == null;
        boolean needs2FA = Boolean.TRUE.equals(account.getTwoFactorEnabled());
        log.debug("accountId={}, isNewDevice={}, needs2FA={}", account.getId(), isNewDevice, needs2FA);

        if (isNewDevice) {
            AccountDevice newDevice = AccountDevice.create(
                    account.getId(),
                    request.deviceInfo().deviceType(),
                    request.deviceInfo().deviceName(),
                    fingerprint
            );
            account.addDevice(newDevice);
            account = accountRepository.save(account);

            AccountDevice savedDevice = account.findDeviceByFingerprint(fingerprint).orElseThrow();
            log.info("New device login for accountId={}, deviceId={}, OTP required", account.getId(), savedDevice.getId());
            return sendVerificationAndRespond(account, savedDevice);
        }

        if (needs2FA) {
            existingDevice.touch();
            account = accountRepository.save(account);

            AccountDevice savedDevice = account.findDeviceByFingerprint(fingerprint).orElseThrow();
            log.info("2FA required for accountId={}, deviceId={}", account.getId(), savedDevice.getId());
            return sendVerificationAndRespond(account, savedDevice);
        }

        existingDevice.touch();
        account.recordLogin(ipAddress);
        account = accountRepository.save(account);

        AccountDevice savedDevice = account.findDeviceByFingerprint(fingerprint).orElseThrow();

        accountLoginHistoryRepository.save(
                AccountLoginHistory.createSuccess(account.getId(), savedDevice.getId(), ipAddress, null)
        );

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                account.getId(), savedDevice.getId(),
                tokenProvider.hashToken(refreshToken), ipAddress
        );
        accountSessionRepository.save(session);

        String accessToken = tokenProvider.generateAccessToken(
                account.getId(), account.getUuid(), account.getEmail()
        );

        log.info("Login successful for accountId={}, accountUuid={}", account.getId(), account.getUuid());
        return LoginAccountResponse.authenticated(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }

    private void validateAccountStatus(Account account) {
        switch (account.getStatus()) {
            case PENDING -> {
                log.warn("Login rejected: account pending for accountId={}", account.getId());
                throw AccountError.ACCOUNT_PENDING.exception();
            }
            case SUSPENDED -> {
                log.warn("Login rejected: account suspended for accountId={}", account.getId());
                throw AccountError.ACCOUNT_SUSPENDED.exception();
            }
            case BANNED -> {
                log.warn("Login rejected: account banned for accountId={}", account.getId());
                throw AccountError.ACCOUNT_BANNED.exception();
            }
            case DEACTIVATED -> {
                log.warn("Login rejected: account deactivated for accountId={}", account.getId());
                throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
            }
            case ACTIVE -> { }
        }
    }

    private static String maskEmail(String email) {
        if (email == null || email.length() < 3) return "***";
        int at = email.indexOf('@');
        if (at <= 0) return "***";
        return email.substring(0, Math.min(2, at)) + "***" + email.substring(at);
    }

    private LoginAccountResponse sendVerificationAndRespond(Account account, AccountDevice device) {
        log.debug("Sending login OTP for accountId={}", account.getId());
        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.LOGIN, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(account.getEmail(), code, OtpPurpose.LOGIN);

        String verificationToken = tokenProvider.generateVerificationToken(account.getId(), device.getId());
        return LoginAccountResponse.verificationRequired(verificationToken);
    }
}
