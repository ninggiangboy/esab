package dev.ngb.app.identity.application.usecase.register_account.dto;

public record RegisterAccountRequest(
        String email,
        String password
) {}
