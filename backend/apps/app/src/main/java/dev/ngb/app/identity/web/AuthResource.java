package dev.ngb.app.identity.web;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.usecase.authentication.login_account.LoginAccountUseCase;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountRequest;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountResponse;
import dev.ngb.app.identity.application.usecase.authentication.oauth_login.OAuthLoginUseCase;
import dev.ngb.app.identity.application.usecase.authentication.oauth_login.dto.OAuthLoginRequest;
import dev.ngb.app.identity.application.usecase.authentication.oauth_login.dto.OAuthLoginResponse;
import dev.ngb.app.identity.application.usecase.authentication.verify_login.VerifyLoginUseCase;
import dev.ngb.app.identity.application.usecase.authentication.verify_login.dto.VerifyLoginRequest;
import dev.ngb.app.identity.application.usecase.password.forgot_password.ForgotPasswordUseCase;
import dev.ngb.app.identity.application.usecase.password.forgot_password.dto.ForgotPasswordRequest;
import dev.ngb.app.identity.application.usecase.password.reset_password.ResetPasswordUseCase;
import dev.ngb.app.identity.application.usecase.password.reset_password.dto.ResetPasswordRequest;
import dev.ngb.app.identity.application.usecase.registration.register_account.RegisterAccountUseCase;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountResponse;
import dev.ngb.app.identity.application.usecase.registration.resend_verification.ResendVerificationUseCase;
import dev.ngb.app.identity.application.usecase.registration.resend_verification.dto.ResendVerificationRequest;
import dev.ngb.app.identity.application.usecase.registration.verify_email.VerifyEmailUseCase;
import dev.ngb.app.identity.application.usecase.registration.verify_email.dto.VerifyEmailRequest;
import dev.ngb.app.identity.application.usecase.session.logout_account.LogoutAccountUseCase;
import dev.ngb.app.identity.application.usecase.session.logout_account.dto.LogoutAccountRequest;
import dev.ngb.app.identity.application.usecase.session.refresh_token.RefreshTokenUseCase;
import dev.ngb.app.identity.application.usecase.session.refresh_token.dto.RefreshTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthResource implements AuthApi {

    private final RegisterAccountUseCase registerAccountUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final ResendVerificationUseCase resendVerificationUseCase;
    private final LoginAccountUseCase loginAccountUseCase;
    private final VerifyLoginUseCase verifyLoginUseCase;
    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutAccountUseCase logoutAccountUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @Override
    public ResponseEntity<RegisterAccountResponse> register(RegisterAccountRequest request) {
        RegisterAccountResponse response = registerAccountUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<AuthTokenResponse> verifyEmail(VerifyEmailRequest request,
                                                         HttpServletRequest httpRequest) {
        AuthTokenResponse response = verifyEmailUseCase.execute(request, extractIpAddress(httpRequest));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> resendVerification(ResendVerificationRequest request) {
        resendVerificationUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LoginAccountResponse> login(LoginAccountRequest request,
                                                      HttpServletRequest httpRequest) {
        LoginAccountResponse response = loginAccountUseCase.execute(request, extractIpAddress(httpRequest));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthTokenResponse> verifyLogin(VerifyLoginRequest request,
                                                         HttpServletRequest httpRequest) {
        AuthTokenResponse response = verifyLoginUseCase.execute(request, extractIpAddress(httpRequest));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<OAuthLoginResponse> oauthLogin(OAuthLoginRequest request,
                                                         HttpServletRequest httpRequest) {
        OAuthLoginResponse response = oAuthLoginUseCase.execute(request, extractIpAddress(httpRequest));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthTokenResponse> refreshToken(RefreshTokenRequest request) {
        AuthTokenResponse response = refreshTokenUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> logout(LogoutRequest request) {
        String refreshToken = request != null ? request.refreshToken() : null;
        logoutAccountUseCase.execute(new LogoutAccountRequest(refreshToken));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> forgotPassword(ForgotPasswordRequest request) {
        forgotPasswordUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> resetPassword(ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    private String extractIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
