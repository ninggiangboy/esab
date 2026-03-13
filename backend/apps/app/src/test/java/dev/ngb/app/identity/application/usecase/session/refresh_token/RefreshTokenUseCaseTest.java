package dev.ngb.app.identity.application.usecase.session.refresh_token;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.session.refresh_token.dto.RefreshTokenRequest;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.account.AccountStatus;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private RefreshTokenUseCase useCase;

    @Test
    void shouldRefreshTokenSuccessfully() {
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                10L, 20L, "old-hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        Account account = Account.reconstruct(
                10L, "acc-uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new RefreshTokenRequest("old-refresh-token");

        when(tokenProvider.hashToken("old-refresh-token")).thenReturn("old-hash");
        when(accountSessionRepository.findByTokenHash("old-hash")).thenReturn(Optional.of(session));
        when(accountRepository.findById(10L)).thenReturn(Optional.of(account));
        when(tokenProvider.generateRefreshToken()).thenReturn("new-refresh");
        when(tokenProvider.hashToken("new-refresh")).thenReturn("new-hash");
        when(tokenProvider.generateAccessToken(10L, "acc-uuid", "user@test.com")).thenReturn("new-access");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        AuthTokenResponse response = useCase.execute(request);

        assertThat(response.accessToken()).isEqualTo("new-access");
        assertThat(response.refreshToken()).isEqualTo("new-refresh");
        assertThat(response.expiresIn()).isEqualTo(900L);
        verify(accountSessionRepository, times(2)).save(any());
    }

    @Test
    void shouldThrowWhenSessionNotFound() {
        var request = new RefreshTokenRequest("unknown-token");
        when(tokenProvider.hashToken("unknown-token")).thenReturn("unknown-hash");
        when(accountSessionRepository.findByTokenHash("unknown-hash")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_REFRESH_TOKEN);
    }

    @Test
    void shouldThrowWhenSessionRevoked() {
        AccountSession revokedSession = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                10L, 20L, "hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), true
        );
        var request = new RefreshTokenRequest("revoked-token");

        when(tokenProvider.hashToken("revoked-token")).thenReturn("hash");
        when(accountSessionRepository.findByTokenHash("hash")).thenReturn(Optional.of(revokedSession));

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_REFRESH_TOKEN);
    }

    @Test
    void shouldThrowWhenSessionExpired() {
        AccountSession expiredSession = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                10L, 20L, "hash", "192.168.1.1",
                Instant.now().minus(Duration.ofDays(1)), false
        );
        var request = new RefreshTokenRequest("expired-token");

        when(tokenProvider.hashToken("expired-token")).thenReturn("hash");
        when(accountSessionRepository.findByTokenHash("hash")).thenReturn(Optional.of(expiredSession));

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_REFRESH_TOKEN);
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                999L, 20L, "hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        var request = new RefreshTokenRequest("token");

        when(tokenProvider.hashToken("token")).thenReturn("hash");
        when(accountSessionRepository.findByTokenHash("hash")).thenReturn(Optional.of(session));
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_FOUND);
    }

    @Test
    void shouldThrowWhenAccountNotActive() {
        AccountSession session = AccountSession.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                10L, 20L, "hash", "192.168.1.1",
                Instant.now().plus(Duration.ofDays(30)), false
        );
        Account suspendedAccount = Account.reconstruct(
                10L, "uuid", null, Instant.now(), null, null,
                "user@test.com", null, "hash", AccountStatus.SUSPENDED,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new RefreshTokenRequest("token");

        when(tokenProvider.hashToken("token")).thenReturn("hash");
        when(accountSessionRepository.findByTokenHash("hash")).thenReturn(Optional.of(session));
        when(accountRepository.findById(10L)).thenReturn(Optional.of(suspendedAccount));

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_ACTIVE);
    }
}
