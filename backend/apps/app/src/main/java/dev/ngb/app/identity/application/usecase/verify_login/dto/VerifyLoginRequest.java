package dev.ngb.app.identity.application.usecase.verify_login.dto;

public record VerifyLoginRequest(
        String verificationToken,
        String otpCode
) {}
