package dev.ngb.app.identity.application.usecase.logout_account;

import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.logout_account.dto.LogoutAccountRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogoutAccountUseCase implements UseCaseService {

    private final AccountSessionRepository accountSessionRepository;
    private final TokenProvider tokenProvider;

    public void execute(LogoutAccountRequest request) {
        if (request.refreshToken() == null) {
            return;
        }

        String tokenHash = tokenProvider.hashToken(request.refreshToken());
        accountSessionRepository.findByTokenHash(tokenHash)
                .ifPresent(session -> {
                    session.revoke();
                    accountSessionRepository.save(session);
                });
    }
}
