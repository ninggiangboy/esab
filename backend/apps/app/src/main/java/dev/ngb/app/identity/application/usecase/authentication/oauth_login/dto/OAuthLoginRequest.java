package dev.ngb.app.identity.application.usecase.authentication.oauth_login.dto;

import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.domain.identity.model.auth.AuthProvider;

public record OAuthLoginRequest(
        AuthProvider provider,
        String providerToken,
        DeviceInfo deviceInfo
) {}
