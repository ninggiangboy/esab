package dev.ngb.domain.identity.model.account;

import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @Test
    void shouldCreateAccountWithPendingStatus() {
        Account account = Account.create("user@test.com", "hashed-pw");

        assertThat(account.getEmail()).isEqualTo("user@test.com");
        assertThat(account.getPasswordHash()).isEqualTo("hashed-pw");
        assertThat(account.getStatus()).isEqualTo(AccountStatus.PENDING);
        assertThat(account.getEmailVerified()).isFalse();
        assertThat(account.getPhoneVerified()).isFalse();
        assertThat(account.getTwoFactorEnabled()).isFalse();
        assertThat(account.getCredentials()).isEmpty();
        assertThat(account.getCreatedAt()).isNotNull();
        assertThat(account.getUuid()).isNotNull();
    }

    @Test
    void shouldCreateOAuthAccountWithActiveStatus() {
        Account account = Account.createFromOAuth("oauth@test.com");

        assertThat(account.getEmail()).isEqualTo("oauth@test.com");
        assertThat(account.getPasswordHash()).isNull();
        assertThat(account.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(account.getEmailVerified()).isTrue();
        assertThat(account.getPhoneVerified()).isFalse();
        assertThat(account.getTwoFactorEnabled()).isFalse();
        assertThat(account.getCredentials()).isEmpty();
        assertThat(account.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldActivateAccountWhenStatusPending() {
        Account account = Account.create("user@test.com", "hashed-pw");

        account.activate();

        assertThat(account.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(account.getEmailVerified()).isTrue();
        assertThat(account.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenActivatingNonPendingAccount() {
        Account account = Account.createFromOAuth("oauth@test.com");

        assertThatThrownBy(account::activate)
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_ACTIVE);
    }

    @Test
    void shouldRecordLoginDetails() {
        Account account = Account.create("user@test.com", "hashed-pw");

        account.recordLogin("192.168.1.1");

        assertThat(account.getLastLoginIp()).isEqualTo("192.168.1.1");
        assertThat(account.getLastLoginAt()).isNotNull();
        assertThat(account.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldChangePassword() {
        Account account = Account.create("user@test.com", "old-hash");

        account.changePassword("new-hash");

        assertThat(account.getPasswordHash()).isEqualTo("new-hash");
        assertThat(account.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldReturnTrueWhenAccountIsActive() {
        Account account = Account.createFromOAuth("oauth@test.com");

        assertThat(account.isActive()).isTrue();
        assertThat(account.isPending()).isFalse();
    }

    @Test
    void shouldReturnTrueWhenAccountIsPending() {
        Account account = Account.create("user@test.com", "hashed-pw");

        assertThat(account.isPending()).isTrue();
        assertThat(account.isActive()).isFalse();
    }

    @Test
    void shouldReconstructAccountWithAllFields() {
        Account account = Account.reconstruct(
                1L, "uuid-123", 1L, null, null, null,
                "user@test.com", "+1234567890", "hash", AccountStatus.ACTIVE,
                true, false, false, null, null,
                new java.util.HashSet<>()
        );

        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getUuid()).isEqualTo("uuid-123");
        assertThat(account.getEmail()).isEqualTo("user@test.com");
        assertThat(account.getPhoneNumber()).isEqualTo("+1234567890");
        assertThat(account.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(account.getEmailVerified()).isTrue();
    }
}
