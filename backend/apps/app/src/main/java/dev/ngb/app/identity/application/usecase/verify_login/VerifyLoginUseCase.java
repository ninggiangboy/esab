package dev.ngb.app.identity.application.usecase.verify_login;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.verify_login.dto.VerifyLoginRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.otp.AccountOtp;
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
public class VerifyLoginUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final AccountLoginHistoryRepository accountLoginHistoryRepository;
    private final TokenProvider tokenProvider;

    public AuthTokenResponse execute(VerifyLoginRequest request, String ipAddress) {
        TokenProvider.VerificationClaims claims;
        try {
            claims = tokenProvider.parseVerificationToken(request.verificationToken());
        } catch (Exception e) {
            throw AccountError.INVALID_VERIFICATION_TOKEN.exception();
        }

        Account account = accountRepository.findById(claims.accountId())
                .orElseThrow(AccountError.ACCOUNT_NOT_FOUND::exception);

        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(account.getId(), OtpPurpose.LOGIN)
                .orElseThrow(AccountError.INVALID_OTP::exception);

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);

        AccountDevice device = account.getDevices().stream()
                .filter(d -> d.getId().equals(claims.deviceId()))
                .findFirst()
                .orElseThrow(AccountError.INVALID_VERIFICATION_TOKEN::exception);

        if (!Boolean.TRUE.equals(device.getIsTrusted())) {
            device.markTrusted();
            accountRepository.save(account);
        }

        account.recordLogin(ipAddress);
        account = accountRepository.save(account);

        accountLoginHistoryRepository.save(
                AccountLoginHistory.create(account.getId(), device.getId(), ipAddress, null, LoginResult.SUCCESS, null)
        );

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                account.getId(), device.getId(),
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
