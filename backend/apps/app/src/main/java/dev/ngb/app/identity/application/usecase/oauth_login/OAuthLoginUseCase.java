package dev.ngb.app.identity.application.usecase.oauth_login;

import dev.ngb.app.identity.application.port.OAuthProviderVerifier;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginRequest;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountCredential;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OAuthLoginUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;
    private final OAuthProviderVerifier oAuthProviderVerifier;

    public OAuthLoginResponse execute(OAuthLoginRequest request, String ipAddress) {
        OAuthProviderVerifier.OAuthUserInfo userInfo;
        try {
            userInfo = oAuthProviderVerifier.verify(request.provider(), request.providerToken());
        } catch (Exception e) {
            throw AccountError.INVALID_OAUTH_TOKEN.exception();
        }

        Optional<Account> existingAccount = accountRepository.findByEmail(userInfo.email());
        boolean isNewAccount = existingAccount.isEmpty();

        Account account;
        if (isNewAccount) {
            account = Account.createFromOAuth(userInfo.email());
            AccountCredential credential = AccountCredential.create(
                    null, request.provider(), userInfo.providerAccountId(), request.providerToken(), null
            );
            account.getCredentials().add(credential);
            account = accountRepository.save(account);
        } else {
            account = existingAccount.get();
            if (!account.isActive()) {
                throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
            }
            boolean hasProvider = account.getCredentials().stream()
                    .anyMatch(c -> c.getProvider() == request.provider());
            if (!hasProvider) {
                AccountCredential credential = AccountCredential.create(
                        account.getId(), request.provider(), userInfo.providerAccountId(),
                        request.providerToken(), null
                );
                account.getCredentials().add(credential);
                account = accountRepository.save(account);
            }
        }

        String fingerprint = request.deviceInfo().fingerprint();
        AccountDevice device = account.getDevices().stream()
                .filter(d -> fingerprint.equals(d.getFingerprint()))
                .findFirst()
                .orElse(null);

        if (device == null) {
            AccountDevice newDevice = AccountDevice.create(
                    account.getId(),
                    request.deviceInfo().deviceType(),
                    request.deviceInfo().deviceName(),
                    fingerprint
            );
            newDevice.markTrusted();
            account.getDevices().add(newDevice);
            account.recordLogin(ipAddress);
            account = accountRepository.save(account);

            device = account.getDevices().stream()
                    .filter(d -> fingerprint.equals(d.getFingerprint()))
                    .findFirst()
                    .orElseThrow();
        } else {
            device.touch();
            account.recordLogin(ipAddress);
            account = accountRepository.save(account);

            device = account.getDevices().stream()
                    .filter(d -> fingerprint.equals(d.getFingerprint()))
                    .findFirst()
                    .orElseThrow();
        }

        String refreshToken = tokenProvider.generateRefreshToken();
        AccountSession session = AccountSession.create(
                account.getId(), device.getId(),
                tokenProvider.hashToken(refreshToken), ipAddress
        );
        accountSessionRepository.save(session);

        String accessToken = tokenProvider.generateAccessToken(
                account.getId(), account.getUuid(), account.getEmail()
        );

        return new OAuthLoginResponse(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid(), isNewAccount
        );
    }
}
