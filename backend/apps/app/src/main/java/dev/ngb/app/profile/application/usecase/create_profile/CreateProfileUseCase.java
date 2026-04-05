package dev.ngb.app.profile.application.usecase.create_profile;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.application.UseCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CreateProfileUseCase implements UseCaseService {

    public CreateProfileResponse execute(Long accountId, CreateProfileRequest request) {
        return null;
    }
}

