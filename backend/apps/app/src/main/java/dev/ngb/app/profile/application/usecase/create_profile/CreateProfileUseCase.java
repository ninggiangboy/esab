package dev.ngb.app.profile.application.usecase.create_profile;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.application.UseCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Creates a profile for an already authenticated account. The caller is expected to have
 * resolved and authorized accountId before invoking this use case.
 *
 * A real implementation would enforce domain rules (e.g. profile does not already exist),
 * map the request into the profile aggregate or entity, persist it, and return whatever
 * public identifiers or snapshots the API contract needs.
 *
 * This class is still a stub; the comment documents the intended flow for consistency
 * with other use cases in the module.
 */
@Slf4j
@RequiredArgsConstructor
public class CreateProfileUseCase implements UseCaseService {

    public CreateProfileResponse execute(Long accountId, CreateProfileRequest request) {
        // Stub: wire domain validation, persistence, and response mapping when the profile model exists.
        return null;
    }
}

