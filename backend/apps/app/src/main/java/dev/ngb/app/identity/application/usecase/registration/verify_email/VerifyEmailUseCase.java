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

@RequiredArgsConstructor
public class VerifyEmailUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;

    public AuthTokenResponse execute(VerifyEmailRequest request, String ipAddress) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(AccountError.ACCOUNT_NOT_FOUND::exception);

        if (!account.isPending()) {
            throw AccountError.EMAIL_ALREADY_VERIFIED.exception();
        }

        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(account.getId(), OtpPurpose.REGISTRATION)
                .orElseThrow(AccountError.INVALID_OTP::exception);

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);

        account.activate();

        AccountDevice device = AccountDevice.create(
                account.getId(),
                request.deviceInfo().deviceType(),
                request.deviceInfo().deviceName(),
                request.deviceInfo().fingerprint()
        );
        device.markTrusted();
        account.getDevices().add(device);
        account = accountRepository.save(account);

        AccountDevice savedDevice = account.getDevices().stream()
                .filter(d -> request.deviceInfo().fingerprint().equals(d.getFingerprint()))
                .findFirst()
                .orElseThrow();

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                account.getId(), savedDevice.getId(),
                tokenProvider.hashToken(refreshToken), ipAddress
        );
        accountSessionRepository.save(session);

        String accessToken = tokenProvider.generateAccessToken(
                account.getId(), account.getUuid(), account.getEmail()
        );

        return new AuthTokenResponse(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }
}
