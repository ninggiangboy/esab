package dev.ngb.app.identity.application.usecase.registration.verify_email;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.registration.verify_email.dto.VerifyEmailRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VerifyEmailUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;

    public AuthTokenResponse execute(VerifyEmailRequest request, String ipAddress) {
        log.info("Verify email attempt for email={}", request.email() != null ? request.email().replaceAll("(?<=.).(?=.*@)", "*") : "***");

        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Verify email failed: account not found");
                    return AccountError.ACCOUNT_NOT_FOUND.exception();
                });

        if (!account.isPending()) {
            log.warn("Verify email failed: email already verified accountId={}", account.getId());
            throw AccountError.EMAIL_ALREADY_VERIFIED.exception();
        }

        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(account.getId(), OtpPurpose.REGISTRATION)
                .orElseThrow(() -> {
                    log.warn("Verify email failed: no active OTP for accountId={}", account.getId());
                    return AccountError.INVALID_OTP.exception();
                });

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);
        log.debug("Registration OTP verified for accountId={}", account.getId());

        account.activate();

        AccountDevice device = AccountDevice.create(
                account.getId(),
                request.deviceInfo().deviceType(),
                request.deviceInfo().deviceName(),
                request.deviceInfo().fingerprint()
        );
        device.markTrusted();
        account.addDevice(device);
        account = accountRepository.save(account);

        AccountDevice savedDevice = account.findDeviceByFingerprint(request.deviceInfo().fingerprint()).orElseThrow();

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                account.getId(), savedDevice.getId(),
                tokenProvider.hashToken(refreshToken), ipAddress
        );
        accountSessionRepository.save(session);

        String accessToken = tokenProvider.generateAccessToken(
                account.getId(), account.getUuid(), account.getEmail()
        );

        log.info("Verify email successful accountId={}, accountUuid={}", account.getId(), account.getUuid());
        return new AuthTokenResponse(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }
}
