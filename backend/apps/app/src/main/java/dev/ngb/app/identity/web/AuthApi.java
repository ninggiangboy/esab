package dev.ngb.app.identity.web;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.usecase.forgot_password.dto.ForgotPasswordRequest;
import dev.ngb.app.identity.application.usecase.login_account.dto.LoginAccountRequest;
import dev.ngb.app.identity.application.usecase.login_account.dto.LoginAccountResponse;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginRequest;
import dev.ngb.app.identity.application.usecase.oauth_login.dto.OAuthLoginResponse;
import dev.ngb.app.identity.application.usecase.refresh_token.dto.RefreshTokenRequest;
import dev.ngb.app.identity.application.usecase.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.register_account.dto.RegisterAccountResponse;
import dev.ngb.app.identity.application.usecase.resend_verification.dto.ResendVerificationRequest;
import dev.ngb.app.identity.application.usecase.reset_password.dto.ResetPasswordRequest;
import dev.ngb.app.identity.application.usecase.verify_email.dto.VerifyEmailRequest;
import dev.ngb.app.identity.application.usecase.verify_login.dto.VerifyLoginRequest;
import dev.ngb.infrastructure.web.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "Account registration, login, verification, and password management")
@RequestMapping("/api/auth")
public interface AuthApi {

    @Operation(summary = "Register a new account", description = "Creates a new account with email and password. An OTP is sent to the email for verification.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created, verification email sent"),
            @ApiResponse(responseCode = "409", description = "Email already registered",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    ResponseEntity<RegisterAccountResponse> register(@RequestBody RegisterAccountRequest request);

    @Operation(summary = "Verify email with OTP", description = "Verifies the account email using the OTP code. Returns auth tokens on success.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email verified, tokens issued"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Invalid or expired OTP",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already verified",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/verify-email")
    ResponseEntity<AuthTokenResponse> verifyEmail(@RequestBody VerifyEmailRequest request,
                                                  HttpServletRequest httpRequest);

    @Operation(summary = "Resend verification email", description = "Resends the OTP verification email to the specified address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verification email resent"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already verified",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/verify-email/resend")
    ResponseEntity<Void> resendVerification(@RequestBody ResendVerificationRequest request);

    @Operation(summary = "Login with email and password",
            description = "Authenticates using credentials. Returns tokens directly if 2FA is not required, or a verification token if OTP verification is needed.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated or verification required"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Account not active, suspended, or banned",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    ResponseEntity<LoginAccountResponse> login(@RequestBody LoginAccountRequest request,
                                               HttpServletRequest httpRequest);

    @Operation(summary = "Verify login OTP", description = "Completes two-factor login by verifying the OTP code sent after the initial login request.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login verified, tokens issued"),
            @ApiResponse(responseCode = "422", description = "Invalid or expired OTP / verification token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "429", description = "Max OTP attempts exceeded",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login/verify")
    ResponseEntity<AuthTokenResponse> verifyLogin(@RequestBody VerifyLoginRequest request,
                                                  HttpServletRequest httpRequest);

    @Operation(summary = "Login via OAuth provider", description = "Authenticates using a third-party OAuth provider token (e.g. Google, GitHub). Creates a new account if one does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated via OAuth"),
            @ApiResponse(responseCode = "401", description = "Invalid OAuth provider token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already linked to another account",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/oauth")
    ResponseEntity<OAuthLoginResponse> oauthLogin(@RequestBody OAuthLoginRequest request,
                                                  HttpServletRequest httpRequest);

    @Operation(summary = "Refresh access token", description = "Issues a new access token using a valid refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New tokens issued"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/token/refresh")
    ResponseEntity<AuthTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request);

    @Operation(summary = "Logout", description = "Revokes the current session. If a refresh token is provided, only that session is revoked.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logged out successfully"),
            @ApiResponse(responseCode = "401", description = "Session already revoked",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestBody(required = false) LogoutRequest request);

    @Operation(summary = "Request password reset", description = "Sends a password reset OTP to the specified email address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reset email sent if account exists"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/forgot-password")
    ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request);

    @Operation(summary = "Reset password", description = "Resets the account password using email, OTP code, and the new password.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Invalid OTP or password same as old",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/reset-password")
    ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request);

    record LogoutRequest(String refreshToken) {}
}
