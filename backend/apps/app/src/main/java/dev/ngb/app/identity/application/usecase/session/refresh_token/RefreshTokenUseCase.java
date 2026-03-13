package dev.ngb.app.identity.application.usecase.session.refresh_token;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.session.refresh_token.dto.RefreshTokenRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;

    public AuthTokenResponse execute(RefreshTokenRequest request) {
        String tokenHash = tokenProvider.hashToken(request.refreshToken());

        AccountSession session = accountSessionRepository.findByTokenHash(tokenHash)
                .orElseThrow(AccountError.INVALID_REFRESH_TOKEN::exception);

        if (!session.isValid()) {
            throw AccountError.INVALID_REFRESH_TOKEN.exception();
        }

        Account account = accountRepository.findById(session.getAccountId())
                .orElseThrow(AccountError.ACCOUNT_NOT_FOUND::exception);

        if (!account.isActive()) {
            throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
        }

        session.revoke();
        accountSessionRepository.save(session);

        String newRefreshToken = tokenProvider.generateRefreshToken();
        AccountSession newSession = AccountSession.create(
                account.getId(), session.getDeviceId(),
                tokenProvider.hashToken(newRefreshToken), session.getIpAddress()
        );
        accountSessionRepository.save(newSession);

        String accessToken = tokenProvider.generateAccessToken(
                account.getId(), account.getUuid(), account.getEmail()
        );

        return new AuthTokenResponse(
                accessToken, newRefreshToken,
                tokenProvider.getAccessTokenExpiresInSeconds(),
                account.getUuid()
        );
    }
}
