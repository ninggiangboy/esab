package dev.ngb.domain.identity.model.account;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class AccountCredentialTest {

    @Test
    void shouldCreateCredentialWithAllFields() {
        AccountCredential credential = AccountCredential.create(
                1L, AuthProvider.GOOGLE, "google-id-123", "access-token", "refresh-token"
        );

        assertThat(credential.getAccountId()).isEqualTo(1L);
        assertThat(credential.getProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(credential.getProviderAccountId()).isEqualTo("google-id-123");
        assertThat(credential.getAccessToken()).isEqualTo("access-token");
        assertThat(credential.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(credential.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldCreateCredentialWithNullOptionalFields() {
        AccountCredential credential = AccountCredential.create(
                null, AuthProvider.APPLE, "apple-id", "token", null
        );

        assertThat(credential.getAccountId()).isNull();
        assertThat(credential.getRefreshToken()).isNull();
    }

    @Test
    void shouldReconstructCredentialWithAllFields() {
        Instant now = Instant.now();
        AccountCredential credential = AccountCredential.reconstruct(
                1L, "uuid", null, now, null, now,
                10L, AuthProvider.GITHUB, "gh-123", "access", "refresh"
        );

        assertThat(credential.getId()).isEqualTo(1L);
        assertThat(credential.getAccountId()).isEqualTo(10L);
        assertThat(credential.getProvider()).isEqualTo(AuthProvider.GITHUB);
    }
}
