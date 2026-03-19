package dev.ngb.app.profile.application.usecase.create_profile;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.app.common.public_api.IdentityPublicApi;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.profile.error.ProfileError;
import dev.ngb.domain.profile.model.profile.Profile;
import dev.ngb.domain.profile.model.stats.ProfileStats;
import dev.ngb.domain.profile.repository.ProfileRepository;
import dev.ngb.domain.profile.repository.ProfileStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CreateProfileUseCase implements UseCaseService {

    public CreateProfileResponse execute(Long accountId, CreateProfileRequest request) {
        return null;
    }
}

