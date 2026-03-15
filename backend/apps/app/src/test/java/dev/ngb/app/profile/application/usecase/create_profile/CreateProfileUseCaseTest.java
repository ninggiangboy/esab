package dev.ngb.app.profile.application.usecase.create_profile;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.app.public_api.IdentityPublicApi;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.profile.error.ProfileError;
import dev.ngb.domain.profile.model.profile.Profile;
import dev.ngb.domain.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProfileUseCaseTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private IdentityPublicApi identityPublicApi;

    @InjectMocks
    private CreateProfileUseCase useCase;

    @Test
    void shouldCreateProfileSuccessfully() {
        Long accountId = 1L;
        var request = new CreateProfileRequest("user1", "User One");

        when(identityPublicApi.isAccountActive(accountId)).thenReturn(true);
        when(profileRepository.existsByAccountId(accountId)).thenReturn(false);
        when(profileRepository.existsByUsername("user1")).thenReturn(false);
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreateProfileResponse response = useCase.execute(accountId, request);

        assertThat(response.profileUuid()).isNotNull();
        verify(identityPublicApi).isAccountActive(accountId);
        verify(profileRepository).existsByAccountId(accountId);
        verify(profileRepository).existsByUsername("user1");
        verify(profileRepository).save(any(Profile.class));
    }

    @Test
    void shouldThrowWhenAccountNotActiveOrNotFound() {
        Long accountId = 1L;
        var request = new CreateProfileRequest("user1", "User One");

        when(identityPublicApi.isAccountActive(accountId)).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(accountId, request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(AccountError.ACCOUNT_NOT_ACTIVE);

        verify(profileRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenProfileAlreadyExistsForAccount() {
        Long accountId = 1L;
        var request = new CreateProfileRequest("user1", "User One");

        when(identityPublicApi.isAccountActive(accountId)).thenReturn(true);
        when(profileRepository.existsByAccountId(accountId)).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(accountId, request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(ProfileError.PROFILE_ALREADY_EXISTS);

        verify(profileRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenUsernameBlank() {
        Long accountId = 1L;
        var request = new CreateProfileRequest("   ", "User One");

        when(identityPublicApi.isAccountActive(accountId)).thenReturn(true);
        when(profileRepository.existsByAccountId(accountId)).thenReturn(false);

        assertThatThrownBy(() -> useCase.execute(accountId, request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(ProfileError.USERNAME_REQUIRED);

        verify(profileRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenUsernameAlreadyExists() {
        Long accountId = 1L;
        var request = new CreateProfileRequest("user1", "User One");

        when(identityPublicApi.isAccountActive(accountId)).thenReturn(true);
        when(profileRepository.existsByAccountId(accountId)).thenReturn(false);
        when(profileRepository.existsByUsername("user1")).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(accountId, request))
                .isInstanceOf(DomainException.class)
                .extracting(e -> ((DomainException) e).getError())
                .isEqualTo(ProfileError.USERNAME_ALREADY_EXISTS);

        verify(profileRepository, never()).save(any());
    }
}

