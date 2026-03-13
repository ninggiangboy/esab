package dev.ngb.app.identity.application.usecase.authentication.verify_login.dto;

public record VerifyLoginRequest(
        String verificationToken,
        String otpCode
) {}
