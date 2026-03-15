package dev.ngb.app.profile.web;

import dev.ngb.app.profile.application.usecase.create_profile.CreateProfileUseCase;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileResource implements ProfileApi {

    private final CreateProfileUseCase createProfileUseCase;

    @Override
    public ResponseEntity<CreateProfileResponse> createProfile(
            CreateProfileRequest request,
            Jwt jwt
    ) {
        Long accountId = jwt.getClaim("account_id");
        CreateProfileResponse response = createProfileUseCase.execute(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

