package dev.ngb.infrastructure.config;

import dev.ngb.application.port.config.AppConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppPropertiesConfig implements AppConfig {
    @Value("${app.security.jwt.access-token-expiry:900}")
    private long securityJwtAccessTokenExpiry;
    @Value("${app.security.jwt.verification-token-expiry:300}")
    private long securityJwtRefreshTokenExpiry;
    @Value("${app.security.jwt.public-key-base64}")
    private String securityJwtPublicKeyBase64;
    @Value("${app.security.jwt.private-key-base64}")
    private String securityJwtPrivateKeyBase64;
}
