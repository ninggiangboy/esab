package dev.ngb.app.identity.application.usecase.authentication.oauth_login;

import dev.ngb.app.identity.application.port.OAuthProviderVerifier;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.authentication.oauth_login.dto.OAuthLoginRequest;
import dev.ngb.app.identity.application.usecase.authentication.oauth_login.dto.OAuthLoginResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountCredential;
import dev.ngb.domain.identity.model.account.AccountDevice;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OAuthLoginUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;
    private final OAuthProviderVerifier oAuthProviderVerifier;

    public OAuthLoginResponse execute(OAuthLoginRequest request, String ipAddress) {
        log.info("OAuth login attempt provider={}", request.provider());

        OAuthProviderVerifier.OAuthUserInfo userInfo;
        try {
            userInfo = oAuthProviderVerifier.verify(request.provider(), request.providerToken());
        } catch (Exception e) {
            log.warn("OAuth verification failed provider={}: {}", request.provider(), e.getMessage());
            throw AccountError.INVALID_OAUTH_TOKEN.exception();
        }

        log.debug("OAuth user verified email={}", userInfo.email());

        Optional<Account> existingAccount = accountRepository.findByEmail(userInfo.email());
        boolean isNewAccount = existingAccount.isEmpty();

        Account account;
        if (isNewAccount) {
            log.debug("Creating new account from OAuth for email={}", userInfo.email());
            account = Account.createFromOAuth(userInfo.email());
            AccountCredential credential = AccountCredential.create(
                    null, request.provider(), userInfo.providerAccountId(), request.providerToken(), null
            );
            account.addCredential(credential);
            account = accountRepository.save(account);
        } else {
            account = existingAccount.get();
            if (!account.isActive()) {
                log.warn("OAuth login rejected: account not active accountId={}", account.getId());
                throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
            }
            boolean hasProvider = account.getCredentials().stream()
                    .anyMatch(c -> c.getProvider() == request.provider());
            if (!hasProvider) {
                log.debug("Linking OAuth provider to existing account accountId={}", account.getId());
                AccountCredential credential = AccountCredential.create(
                        account.getId(), request.provider(), userInfo.providerAccountId(),
                        request.providerToken(), null
                );
                account.addCredential(credential);
                account = accountRepository.save(account);
            }
        }

        String fingerprint = request.deviceInfo().fingerprint();
        AccountDevice device = account.findDeviceByFingerprint(fingerprint).orElse(null);

        if (device == null) {
            log.debug("New device for OAuth login accountId={}", account.getId());
            AccountDevice newDevice = AccountDevice.create(
                    account.getId(),
                    request.deviceInfo().deviceType(),
                    request.deviceInfo().deviceName(),
                    fingerprint
            );
            newDevice.markTrusted();
            account.addDevice(newDevice);
            account.recordLogin(ipAddress);
            account = accountRepository.save(account);

            device = account.findDeviceByFingerprint(fingerprint).orElseThrow();
        } else {
            device.touch();
            account.recordLogin(ipAddress);
            account = accountRepository.save(account);

            device = account.findDeviceByFingerprint(fingerprint).orElseThrow();
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

        log.info("OAuth login successful accountId={}, accountUuid={}, isNewAccount={}", account.getId(), account.getUuid(), isNewAccount);
        return new OAuthLoginResponse(
                accessToken, refreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid(), isNewAccount
        );
    }
}
