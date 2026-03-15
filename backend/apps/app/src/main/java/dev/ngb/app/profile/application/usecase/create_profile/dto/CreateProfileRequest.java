package dev.ngb.app.profile.application.usecase.create_profile.dto;

import dev.ngb.domain.profile.model.profile.ProfileVisibility;

public record CreateProfileRequest(
        String username,
        String displayName,
        String bio,
        ProfileVisibility visibility
) {}

