package dev.ngb.app.identity.application.usecase.password.reset_password.dto;

public record ResetPasswordRequest(
        String email,
        String otpCode,
        String newPassword
) {}
