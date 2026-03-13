package dev.ngb.app.identity.infrastructure.security;

import dev.ngb.app.identity.application.port.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final long accessTokenExpiresInSeconds;
    private final long verificationTokenExpiresInSeconds;

    public JwtTokenProvider(
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder,
            @Value("${app.security.jwt.access-token-expiry:900}") long accessTokenExpiresInSeconds,
            @Value("${app.security.jwt.verification-token-expiry:300}") long verificationTokenExpiresInSeconds
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
        this.verificationTokenExpiresInSeconds = verificationTokenExpiresInSeconds;
    }

    @Override
    public String generateAccessToken(Long accountId, String accountUuid, String email) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(accountUuid)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessTokenExpiresInSeconds))
                .claim("account_id", accountId)
                .claim("email", email)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    @Override
    public String generateRefreshToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Override
    public String generateVerificationToken(Long accountId, Long deviceId) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject("verification")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(verificationTokenExpiresInSeconds))
                .claim("account_id", accountId)
                .claim("device_id", deviceId)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    @Override
    public VerificationClaims parseVerificationToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        Long accountId = jwt.getClaim("account_id");
        Long deviceId = jwt.getClaim("device_id");
        return new VerificationClaims(accountId, deviceId);
    }

    @Override
    public String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    @Override
    public long getAccessTokenExpiresInSeconds() {
        return accessTokenExpiresInSeconds;
    }
}
