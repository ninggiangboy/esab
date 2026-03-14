package dev.ngb.app.identity.application.usecase.session.logout_account;

import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.session.logout_account.dto.LogoutAccountRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LogoutAccountUseCase implements UseCaseService {

    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;

    public void execute(LogoutAccountRequest request) {
        log.debug("Logout attempt");

        if (request.refreshToken() == null) {
            log.debug("Logout: no refresh token provided, skipping");
            return;
        }

        String tokenHash = tokenProvider.hashToken(request.refreshToken());
        accountSessionRepository.findByTokenHash(tokenHash)
                .ifPresentOrElse(
                        session -> {
                            session.revoke();
                            accountSessionRepository.save(session);
                            log.info("Logout successful accountId={}, sessionId={}", session.getAccountId(), session.getId());
                        },
                        () -> log.debug("Logout: no session found for token (already invalid or expired)")
                );
    }
}
