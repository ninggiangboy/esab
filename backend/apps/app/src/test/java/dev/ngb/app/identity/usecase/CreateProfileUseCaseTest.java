package dev.ngb.app.identity.usecase;

import dev.ngb.app.profile.application.usecase.create_profile.CreateProfileUseCase;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.domain.profile.model.profile.ProfileVisibility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateProfileUseCase")
class CreateProfileUseCaseTest {

    private final CreateProfileUseCase useCase = new CreateProfileUseCase();

    @Test
    @DisplayName("execute: stub returns null")
    void executeStubReturnsNull() {
        var request = new CreateProfileRequest("u1", "Name", "bio", ProfileVisibility.PUBLIC);

        assertThat(useCase.execute(1L, request)).isNull();
    }
}
