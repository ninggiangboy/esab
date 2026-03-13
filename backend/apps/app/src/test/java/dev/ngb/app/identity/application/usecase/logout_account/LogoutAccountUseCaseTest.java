package dev.ngb.app.identity.application.usecase.logout_account;

import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.logout_account.dto.LogoutAccountRequest;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutAccountUseCaseTest {

    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private LogoutAccountUseCase useCase;

    @Test
    void shouldRevokeSessionOnLogout() {
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                10L, 20L, "hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        var request = new LogoutAccountRequest("refresh-token");

        when(tokenProvider.hashToken("refresh-token")).thenReturn("hash");
        when(accountSessionRepository.findByTokenHash("hash")).thenReturn(Optional.of(session));

        useCase.execute(request);

        verify(accountSessionRepository).save(session);
    }

    @Test
    void shouldDoNothingWhenRefreshTokenNull() {
        var request = new LogoutAccountRequest(null);

        useCase.execute(request);

        verify(tokenProvider, never()).hashToken(any());
        verify(accountSessionRepository, never()).findByTokenHash(any());
    }

    @Test
    void shouldDoNothingWhenSessionNotFound() {
        var request = new LogoutAccountRequest("unknown-token");

        when(tokenProvider.hashToken("unknown-token")).thenReturn("unknown-hash");
        when(accountSessionRepository.findByTokenHash("unknown-hash")).thenReturn(Optional.empty());

        useCase.execute(request);

        verify(accountSessionRepository, never()).save(any());
    }
}
