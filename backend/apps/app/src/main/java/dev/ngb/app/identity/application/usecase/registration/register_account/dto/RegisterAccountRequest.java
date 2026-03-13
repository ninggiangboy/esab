package dev.ngb.app.identity.application.usecase.registration.register_account.dto;

public record RegisterAccountRequest(
        String email,
        String password
) {}
