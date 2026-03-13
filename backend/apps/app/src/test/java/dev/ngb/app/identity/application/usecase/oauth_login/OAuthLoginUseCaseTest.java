package dev.ngb.app.identity.application.usecase.oauth_login;

import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.app.identity.application.port.OAuthProviderVerifier;
import dev.ngb.app.identity.application.port.TokenProvider;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginRequest;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginResponse;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.*;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthLoginUseCaseTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountSessionRepository accountSessionRepository;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private OAuthProviderVerifier oAuthProviderVerifier;

    @InjectMocks
    private OAuthLoginUseCase useCase;

    private static final String IP = "192.168.1.1";

    @Test
    void shouldCreateNewAccountForNewOAuthUser() {
        var userInfo = new OAuthProviderVerifier.OAuthUserInfo("new@test.com", "google-123");
        var request = new OAuthLoginRequest(AuthProvider.GOOGLE, "provider-token",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-new"));

        when(oAuthProviderVerifier.verify(AuthProvider.GOOGLE, "provider-token")).thenReturn(userInfo);
        when(accountRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account saved = invocation.getArgument(0);
            return Account.reconstruct(
                    1L, saved.getUuid(), null, Instant.now(), null, null,
                    saved.getEmail(), null, null, AccountStatus.ACTIVE,
                    true, false, false, null, null,
                    new HashSet<>(saved.getCredentials()), new HashSet<>(saved.getDevices())
            );
        });
        when(tokenProvider.generateRefreshToken()).thenReturn("refresh");
        when(tokenProvider.hashToken("refresh")).thenReturn("hashed");
        when(tokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        OAuthLoginResponse response = useCase.execute(request, IP);

        assertThat(response.isNewAccount()).isTrue();
        assertThat(response.accessToken()).isEqualTo("access");
        verify(accountSessionRepository).save(any());
    }

    @Test
    void shouldLoginExistingAccountWithNewProvider() {
        var userInfo = new OAuthProviderVerifier.OAuthUserInfo("existing@test.com", "google-456");
        AccountDevice device = AccountDevice.reconstruct(
                1L, "dev-uuid", null, Instant.now(), null, null,
                1L, DeviceType.WEB, "Chrome", "fp-known",
                null, null, Instant.now(), true
        );
        Account account = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "existing@test.com", null, null, AccountStatus.ACTIVE,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>(Set.of(device))
        );
        var request = new OAuthLoginRequest(AuthProvider.GOOGLE, "provider-token",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp-known"));

        when(oAuthProviderVerifier.verify(AuthProvider.GOOGLE, "provider-token")).thenReturn(userInfo);
        when(accountRepository.findByEmail("existing@test.com")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(tokenProvider.generateRefreshToken()).thenReturn("refresh");
        when(tokenProvider.hashToken("refresh")).thenReturn("hashed");
        when(tokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access");
        when(tokenProvider.getAccessTokenExpiresInSeconds()).thenReturn(900L);

        OAuthLoginResponse response = useCase.execute(request, IP);

        assertThat(response.isNewAccount()).isFalse();
        assertThat(response.accessToken()).isEqualTo("access");
    }

    @Test
    void shouldThrowWhenOAuthTokenInvalid() {
        var request = new OAuthLoginRequest(AuthProvider.GOOGLE, "bad-token",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(oAuthProviderVerifier.verify(AuthProvider.GOOGLE, "bad-token"))
                .thenThrow(new RuntimeException("Invalid"));

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.INVALID_OAUTH_TOKEN);
    }

    @Test
    void shouldThrowWhenExistingAccountNotActive() {
        var userInfo = new OAuthProviderVerifier.OAuthUserInfo("suspended@test.com", "google-789");
        Account suspendedAccount = Account.reconstruct(
                1L, "uuid", null, Instant.now(), null, null,
                "suspended@test.com", null, null, AccountStatus.SUSPENDED,
                true, false, false, null, null,
                new HashSet<>(), new HashSet<>()
        );
        var request = new OAuthLoginRequest(AuthProvider.GOOGLE, "token",
                new DeviceInfo(DeviceType.WEB, "Chrome", "fp"));

        when(oAuthProviderVerifier.verify(AuthProvider.GOOGLE, "token")).thenReturn(userInfo);
        when(accountRepository.findByEmail("suspended@test.com")).thenReturn(Optional.of(suspendedAccount));

        assertThatThrownBy(() -> useCase.execute(request, IP))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_ACTIVE);
    }
}
