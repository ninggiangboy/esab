package dev.ngb.application.port.config;

public interface AppConfig {
    long getSecurityJwtAccessTokenExpiry();
    long getSecurityJwtRefreshTokenExpiry();
    String getSecurityJwtPublicKeyBase64();
    String getSecurityJwtPrivateKeyBase64();
}
