package dev.ngb.app.identity.application.usecase.oauth_login.dto;

import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.domain.identity.model.account.AuthProvider;

public record OAuthLoginRequest(
        AuthProvider provider,
        String providerToken,
        DeviceInfo deviceInfo
) {}
