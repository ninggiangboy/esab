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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping("/register")
    ResponseEntity<RegisterAccountResponse> register(@RequestBody RegisterAccountRequest request);

    @PostMapping("/verify-email")
    ResponseEntity<AuthTokenResponse> verifyEmail(@RequestBody VerifyEmailRequest request,
                                                  HttpServletRequest httpRequest);

    @PostMapping("/verify-email/resend")
    ResponseEntity<Void> resendVerification(@RequestBody ResendVerificationRequest request);

    @PostMapping("/login")
    ResponseEntity<LoginAccountResponse> login(@RequestBody LoginAccountRequest request,
                                               HttpServletRequest httpRequest);

    @PostMapping("/login/verify")
    ResponseEntity<AuthTokenResponse> verifyLogin(@RequestBody VerifyLoginRequest request,
                                                  HttpServletRequest httpRequest);

    @PostMapping("/oauth")
    ResponseEntity<OAuthLoginResponse> oauthLogin(@RequestBody OAuthLoginRequest request,
                                                  HttpServletRequest httpRequest);

    @PostMapping("/token/refresh")
    ResponseEntity<AuthTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestBody(required = false) LogoutRequest request);

    @PostMapping("/forgot-password")
    ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request);

    @PostMapping("/reset-password")
    ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request);

    record LogoutRequest(String refreshToken) {}
}
