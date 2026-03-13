package dev.ngb.app.identity.application.usecase.oauth_login.dto;

public record OAuthLoginResponse(
        String accessToken,
        String refreshToken,
        long expiresIn,
        String accountUuid,
        boolean isNewAccount
) {}
