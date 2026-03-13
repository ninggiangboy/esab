package dev.ngb.app.identity.application.usecase.authentication.login_account.dto;

import dev.ngb.app.identity.application.dto.DeviceInfo;

public record LoginAccountRequest(
        String email,
        String password,
        DeviceInfo deviceInfo
) {}
