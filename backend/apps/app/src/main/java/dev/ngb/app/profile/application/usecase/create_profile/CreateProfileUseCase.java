package dev.ngb.app.profile.application.usecase.create_profile;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.application.UseCaseService;
import dev.ngb.app.public_api.IdentityPublicApi;
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

    private final ProfileRepository profileRepository;
    private final ProfileStatsRepository profileStatsRepository;
    private final IdentityPublicApi identityPublicApi;

    public CreateProfileResponse execute(Long accountId, CreateProfileRequest request) {
        log.info("Create profile attempt for accountId={}", accountId);

        if (!identityPublicApi.isAccountActive(accountId)) {
            log.warn("Create profile failed: account not active or not found accountId={}", accountId);
            throw AccountError.ACCOUNT_NOT_ACTIVE.exception();
        }

        if (profileRepository.existsByAccountId(accountId)) {
            log.warn("Create profile failed: profile already exists for accountId={}", accountId);
            throw ProfileError.PROFILE_ALREADY_EXISTS.exception();
        }

        String username = request.username();
        if (username == null || username.isBlank()) {
            log.warn("Create profile failed: username is blank for accountId={}", accountId);
            throw ProfileError.USERNAME_REQUIRED.exception();
        }

        if (profileRepository.existsByUsername(username)) {
            log.warn("Create profile failed: username already exists username={}", username);
            throw ProfileError.USERNAME_ALREADY_EXISTS.exception();
        }

        Profile profile = Profile.createForNewAccount(
                accountId,
                username,
                request.displayName(),
                request.bio(),
                request.visibility()
        );

        profile = profileRepository.save(profile);
        profileStatsRepository.save(ProfileStats.createForNewProfile(profile.getId()));

        log.info("Create profile successful accountId={}, profileUuid={}", accountId, profile.getUuid());
        return new CreateProfileResponse(profile.getUuid());
    }
}

