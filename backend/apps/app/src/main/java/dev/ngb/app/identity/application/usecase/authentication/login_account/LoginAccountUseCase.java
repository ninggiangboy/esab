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
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(AccountError.INVALID_CREDENTIALS::exception);

        if (!passwordEncoder.matches(request.password(), account.getPasswordHash())) {
            accountLoginHistoryRepository.save(
                    AccountLoginHistory.create(account.getId(), null, ipAddress, null, LoginResult.FAILED, "Invalid password")
            );
            throw AccountError.INVALID_CREDENTIALS.exception();
        }

        validateAccountStatus(account);

        String fingerprint = request.deviceInfo().fingerprint();
        AccountDevice existingDevice = account.getDevices().stream()
                .filter(d -> fingerprint.equals(d.getFingerprint()))
                .findFirst()
                .orElse(null);

        boolean isNewDevice = existingDevice == null;
        boolean needs2FA = Boolean.TRUE.equals(account.getTwoFactorEnabled());

        if (isNewDevice) {
            AccountDevice newDevice = AccountDevice.create(
                    account.getId(),
                    request.deviceInfo().deviceType(),
                    request.deviceInfo().deviceName(),
                    fingerprint
            );
            account.getDevices().add(newDevice);
            account = accountRepository.save(account);

            AccountDevice savedDevice = account.getDevices().stream()
                    .filter(d -> fingerprint.equals(d.getFingerprint()))
                    .findFirst()
                    .orElseThrow();

            return sendVerificationAndRespond(account, savedDevice);
        }

        if (needs2FA) {
            existingDevice.touch();
            account = accountRepository.save(account);

            AccountDevice savedDevice = account.getDevices().stream()
                    .filter(d -> fingerprint.equals(d.getFingerprint()))
                    .findFirst()
                    .orElseThrow();

            return sendVerificationAndRespond(account, savedDevice);
        }

        existingDevice.touch();
        account.recordLogin(ipAddress);
        account = accountRepository.save(account);

        AccountDevice savedDevice = account.getDevices().stream()
                .filter(d -> fingerprint.equals(d.getFingerprint()))
                .findFirst()
                .orElseThrow();

        accountLoginHistoryRepository.save(
                AccountLoginHistory.create(account.getId(), savedDevice.getId(), ipAddress, null, LoginResult.SUCCESS, null)
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

        return LoginAccountResponse.authenticated(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }

    private void validateAccountStatus(Account account) {
        switch (account.getStatus()) {
            case PENDING -> throw AccountError.ACCOUNT_PENDING.exception();
            case SUSPENDED -> throw AccountError.ACCOUNT_SUSPENDED.exception();
            case BANNED -> throw AccountError.ACCOUNT_BANNED.exception();
            case DEACTIVATED -> throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
            case ACTIVE -> { }
        }
    }

    private LoginAccountResponse sendVerificationAndRespond(Account account, AccountDevice device) {
        String code = otpCodeGenerator.generate();
        AccountOtp otp = AccountOtp.create(account.getId(), code, OtpPurpose.LOGIN, OtpChannel.EMAIL);
        accountOtpRepository.save(otp);

        otpSender.send(account.getEmail(), code, OtpPurpose.LOGIN);

        String verificationToken = tokenProvider.generateVerificationToken(account.getId(), device.getId());
        return LoginAccountResponse.verificationRequired(verificationToken);
    }
}
