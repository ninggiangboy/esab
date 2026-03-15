package dev.ngb.app.profile.web;

import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileRequest;
import dev.ngb.app.profile.application.usecase.create_profile.dto.CreateProfileResponse;
import dev.ngb.infrastructure.web.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Profile", description = "Profile management")
@RequestMapping("/api/profiles")
public interface ProfileApi {

    @Operation(summary = "Create profile for current account",
            description = "Creates a public profile for the authenticated account using the provided username and display name.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profile created"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Profile or username already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<CreateProfileResponse> createProfile(@RequestBody CreateProfileRequest request,
                                                        @AuthenticationPrincipal Jwt jwt);
}

