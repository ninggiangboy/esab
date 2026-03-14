package dev.ngb.domain.identity.model.account;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.identity.error.AccountError;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Core identity entity representing a user's authentication account, separated from their public profile.
 * <p>
 * Aggregate root of the identity domain. Owns credentials and devices;
 * sessions, OTPs, and login history are operational/audit entities accessed separately.
 * <p>
 * Devices and credentials are exposed as immutable sets; all mutations must go through this aggregate.
 */
@Getter
public class Account extends DomainEntity<Long> {

    private Account() {}

    private String email;
    private String phoneNumber;
    private String passwordHash;
    private AccountStatus status;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean twoFactorEnabled;
    private Instant lastLoginAt;
    private String lastLoginIp;

    private Set<AccountCredential> credentials;
    private Set<AccountDevice> devices;

    /**
     * Returns an immutable view of credentials. Mutations must use {@link #addCredential(AccountCredential)}.
     */
    public Set<AccountCredential> getCredentials() {
        return credentials == null ? Set.of() : Set.copyOf(credentials);
    }

    /**
     * Returns an immutable copy of devices. To add a device use {@link #addDevice(AccountDevice)}.
     */
    public Set<AccountDevice> getDevices() {
        return devices == null ? Set.of() : Set.copyOf(devices);
    }

    public static Account create(String email, String passwordHash) {
        Account obj = new Account();
        obj.createdAt = Instant.now(obj.clock);
        obj.email = email;
        obj.passwordHash = passwordHash;
        obj.status = AccountStatus.PENDING;
        obj.emailVerified = false;
        obj.phoneVerified = false;
        obj.twoFactorEnabled = false;
        obj.credentials = new HashSet<>();
        obj.devices = new HashSet<>();
        return obj;
    }

    public static Account createFromOAuth(String email) {
        Account obj = new Account();
        obj.createdAt = Instant.now(obj.clock);
        obj.email = email;
        obj.status = AccountStatus.ACTIVE;
        obj.emailVerified = true;
        obj.phoneVerified = false;
        obj.twoFactorEnabled = false;
        obj.credentials = new HashSet<>();
        obj.devices = new HashSet<>();
        return obj;
    }

    public void activate() {
        if (this.status != AccountStatus.PENDING) {
            throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
        }
        this.status = AccountStatus.ACTIVE;
        this.emailVerified = true;
        this.updatedAt = Instant.now(clock);
    }

    public void recordLogin(String ip) {
        this.lastLoginAt = Instant.now(clock);
        this.lastLoginIp = ip;
        this.updatedAt = Instant.now(clock);
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.updatedAt = Instant.now(clock);
    }

    public boolean isActive() {
        return this.status == AccountStatus.ACTIVE;
    }

    public boolean isPending() {
        return this.status == AccountStatus.PENDING;
    }

    public Optional<AccountDevice> findDeviceByFingerprint(String fingerprint) {
        if (devices == null || fingerprint == null) return Optional.empty();
        return devices.stream()
                .filter(d -> fingerprint.equals(d.getFingerprint()))
                .findFirst();
    }

    public Optional<AccountDevice> findDeviceById(Long deviceId) {
        if (devices == null || deviceId == null) return Optional.empty();
        return devices.stream()
                .filter(d -> deviceId.equals(d.getId()))
                .findFirst();
    }

    /** Adds a device to this account (mutations go through aggregate root). */
    public void addDevice(AccountDevice device) {
        if (device == null) return;
        if (this.devices == null) this.devices = new HashSet<>();
        this.devices.add(device);
        this.updatedAt = Instant.now(clock);
    }

    /** Adds a credential to this account (mutations go through aggregate root). */
    public void addCredential(AccountCredential credential) {
        if (credential == null) return;
        if (this.credentials == null) this.credentials = new HashSet<>();
        this.credentials.add(credential);
        this.updatedAt = Instant.now(clock);
    }

    public static Account reconstruct(
            Long id, String uuid, Long createdBy, Instant createdAt, Long updatedBy, Instant updatedAt,
            String email, String phoneNumber, String passwordHash, AccountStatus status,
            Boolean emailVerified, Boolean phoneVerified, Boolean twoFactorEnabled,
            Instant lastLoginAt, String lastLoginIp,
            Set<AccountCredential> credentials, Set<AccountDevice> devices) {
        Account obj = new Account();
        obj.id = id;
        obj.uuid = uuid;
        obj.createdBy = createdBy;
        obj.createdAt = createdAt;
        obj.updatedBy = updatedBy;
        obj.updatedAt = updatedAt;
        obj.email = email;
        obj.phoneNumber = phoneNumber;
        obj.passwordHash = passwordHash;
        obj.status = status;
        obj.emailVerified = emailVerified;
        obj.phoneVerified = phoneVerified;
        obj.twoFactorEnabled = twoFactorEnabled;
        obj.lastLoginAt = lastLoginAt;
        obj.lastLoginIp = lastLoginIp;
        obj.credentials = credentials == null ? new HashSet<>() : new HashSet<>(credentials);
        obj.devices = devices == null ? new HashSet<>() : new HashSet<>(devices);
        return obj;
    }
}
