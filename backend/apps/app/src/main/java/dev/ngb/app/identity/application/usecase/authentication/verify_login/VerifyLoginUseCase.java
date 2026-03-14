package dev.ngb.app.identity.application.usecase.authentication.verify_login;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.authentication.verify_login.dto.VerifyLoginRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.model.session.AccountLoginHistory;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.model.session.LoginResult;
import dev.ngb.domain.identity.repository.AccountDeviceRepository;
import dev.ngb.domain.identity.repository.AccountLoginHistoryRepository;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VerifyLoginUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountDeviceRepository accountDeviceRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final AccountLoginHistoryRepository accountLoginHistoryRepository;
    private final TokenProvider tokenProvider;

    public AuthTokenResponse execute(VerifyLoginRequest request, String ipAddress) {
        log.info("Verify login attempt");

        TokenProvider.VerificationClaims claims;
        try {
            claims = tokenProvider.parseVerificationToken(request.verificationToken());
        } catch (Exception e) {
            log.warn("Invalid verification token: {}", e.getMessage());
            throw AccountError.INVALID_VERIFICATION_TOKEN.exception();
        }

        log.debug("Verification token parsed accountId={}, deviceId={}", claims.accountId(), claims.deviceId());

        Account account = accountRepository.findById(claims.accountId())
                .orElseThrow(() -> {
                    log.warn("Account not found for verify login accountId={}", claims.accountId());
                    return AccountError.ACCOUNT_NOT_FOUND.exception();
                });

        Long accountId = account.getId();
        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(accountId, OtpPurpose.LOGIN)
                .orElseThrow(() -> {
                    log.warn("No active login OTP for accountId={}", accountId);
                    return AccountError.INVALID_OTP.exception();
                });

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);
        log.debug("OTP verified for accountId={}", accountId);

        AccountDevice device = accountDeviceRepository.findById(claims.deviceId())
                .filter(d -> accountId.equals(d.getAccountId()))
                .orElseThrow(() -> {
                    log.warn("Device not found for verify login accountId={}, deviceId={}", accountId, claims.deviceId());
                    return AccountError.INVALID_VERIFICATION_TOKEN.exception();
                });

        if (!Boolean.TRUE.equals(device.getIsTrusted())) {
            log.debug("Marking device as trusted accountId={}, deviceId={}", accountId, device.getId());
            device.markTrusted();
            accountDeviceRepository.save(device);
        }

        account.recordLogin(ipAddress);
        account = accountRepository.save(account);

        accountLoginHistoryRepository.save(
                AccountLoginHistory.createSuccess(accountId, device.getId(), ipAddress, null)
        );

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                accountId, device.getId(),
                tokenProvider.hashToken(refreshToken), ipAddress
        );
        accountSessionRepository.save(session);

        String accessToken = tokenProvider.generateAccessToken(
                accountId, account.getUuid(), account.getEmail()
        );

        log.info("Verify login successful accountId={}, accountUuid={}", accountId, account.getUuid());
        return new AuthTokenResponse(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }
}
