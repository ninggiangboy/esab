package dev.ngb.app.identity.application.usecase.registration.verify_email.dto;

import dev.ngb.app.identity.application.dto.DeviceInfo;

public record VerifyEmailRequest(
        String email,
        String otpCode,
        DeviceInfo deviceInfo
) {}
