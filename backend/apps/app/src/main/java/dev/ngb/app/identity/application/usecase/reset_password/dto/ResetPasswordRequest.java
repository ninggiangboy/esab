package dev.ngb.app.identity.application.usecase.reset_password.dto;

public record ResetPasswordRequest(
        String email,
        String otpCode,
        String newPassword
) {}
