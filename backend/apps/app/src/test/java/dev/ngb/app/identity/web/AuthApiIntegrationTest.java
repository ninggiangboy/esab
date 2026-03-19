package dev.ngb.app.identity.web;

import dev.ngb.app.identity.application.dto.AuthTokenResponse;
import dev.ngb.app.identity.application.dto.DeviceInfo;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountRequest;
import dev.ngb.app.identity.application.usecase.authentication.login_account.dto.LoginAccountResponse;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountRequest;
import dev.ngb.app.identity.application.usecase.registration.register_account.dto.RegisterAccountResponse;
import dev.ngb.app.identity.application.usecase.registration.resend_verification.dto.ResendVerificationRequest;
import dev.ngb.app.identity.application.usecase.registration.verify_email.dto.VerifyEmailRequest;
import dev.ngb.app.identity.application.usecase.session.refresh_token.dto.RefreshTokenRequest;
import dev.ngb.app.identity.support.AbstractIdentityIntegrationTest;
import dev.ngb.app.identity.support.TestOtpSender;
import dev.ngb.domain.identity.model.auth.DeviceType;
import dev.ngb.infrastructure.web.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthApiIntegrationTest extends AbstractIdentityIntegrationTest {

    @Autowired
    private TestOtpSender testOtpSender;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String PASSWORD = "SecureP@ss1";

    private String email;

    @BeforeEach
    void setUp() {
        testOtpSender.clear();
        email = "user-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8) + "@example.com";
    }

    @Nested
    class Register {

        @Test
        void shouldReturn201AndAccountUuidWhenRegistrationSucceeds() {
            RegisterAccountRequest request = new RegisterAccountRequest(email, PASSWORD);
            ResponseEntity<RegisterAccountResponse> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/register",
                    request,
                    RegisterAccountResponse.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().accountUuid()).isNotBlank();
        }

        @Test
        void shouldReturn409WhenEmailAlreadyRegistered() {
            RegisterAccountRequest request = new RegisterAccountRequest(email, PASSWORD);
            restTemplate.postForEntity(baseUrl() + "/api/auth/register", request, RegisterAccountResponse.class);

            ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/register",
                    request,
                    ErrorResponse.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(response.getBody()).isNotNull();
        }
    }

    @Nested
    class ResendVerification {

        @Test
        void shouldReturn404WhenAccountNotFound() {
            ResendVerificationRequest request = new ResendVerificationRequest("unknown@example.com");
            ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/verify-email/resend",
                    request,
                    ErrorResponse.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void shouldReturn200WhenResendingForUnverifiedAccount() {
            restTemplate.postForEntity(
                    baseUrl() + "/api/auth/register",
                    new RegisterAccountRequest(email, PASSWORD),
                    RegisterAccountResponse.class
            );

            ResendVerificationRequest request = new ResendVerificationRequest(email);
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/verify-email/resend",
                    request,
                    Void.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        void shouldReturn409WhenEmailAlreadyVerified() throws Exception {
            registerAndVerifyEmail(email, PASSWORD);

            ResendVerificationRequest request = new ResendVerificationRequest(email);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/verify-email/resend",
                    request,
                    String.class
            );

            assertThat(response.getStatusCode())
                    .as("Expected 409 CONFLICT when resending for already-verified email, body: %s", response.getBody())
                    .isEqualTo(HttpStatus.CONFLICT);
        }
    }

    @Nested
    class VerifyEmail {

        @Test
        void shouldReturn200AndTokensWhenOtpIsValid() {
            restTemplate.postForEntity(
                    baseUrl() + "/api/auth/register",
                    new RegisterAccountRequest(email, PASSWORD),
                    RegisterAccountResponse.class
            );
            String otpCode = testOtpSender.getLastOtpCode().orElseThrow();

            VerifyEmailRequest request = new VerifyEmailRequest(
                    email,
                    otpCode,
                    new DeviceInfo(DeviceType.WEB, "Chrome", "fp-1")
            );
            ResponseEntity<AuthTokenResponse> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/verify-email",
                    request,
                    AuthTokenResponse.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().accessToken()).isNotBlank();
            assertThat(response.getBody().refreshToken()).isNotBlank();
            assertThat(response.getBody().accountUuid()).isNotBlank();
        }

        @Test
        void shouldReturn409WhenEmailAlreadyVerified() throws Exception {
            registerAndVerifyEmail(email, PASSWORD);

            String otpCode = testOtpSender.getLastOtpCode().orElse("000000");
            VerifyEmailRequest request = new VerifyEmailRequest(
                    email,
                    otpCode,
                    new DeviceInfo(DeviceType.WEB, "Chrome", "fp-1")
            );
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/verify-email",
                    request,
                    String.class
            );

            assertThat(response.getStatusCode())
                    .as("Expected 409 CONFLICT when verifying already-verified email, body: %s", response.getBody())
                    .isEqualTo(HttpStatus.CONFLICT);
        }
    }

    @Nested
    class Login {

        @Test
        void shouldReturn200WithTokensWhenCredentialsValid() throws Exception {
            registerAndVerifyEmail(email, PASSWORD);

            LoginAccountRequest request = new LoginAccountRequest(
                    email,
                    PASSWORD,
                    new DeviceInfo(DeviceType.WEB, "Chrome", "fp-1")
            );
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/login",
                    request,
                    String.class
            );

            assertThat(response.getStatusCode())
                    .as("Login response: %s", response.getBody())
                    .isEqualTo(HttpStatus.OK);
            LoginAccountResponse body = objectMapper.readValue(response.getBody(), LoginAccountResponse.class);
            assertThat(body).isNotNull();
            assertThat(body.requiresVerification()).isFalse();
            assertThat(body.accessToken()).isNotBlank();
            assertThat(body.refreshToken()).isNotBlank();
        }

        @Test
        void shouldReturn401WhenPasswordInvalid() throws Exception {
            registerAndVerifyEmail(email, PASSWORD);

            LoginAccountRequest request = new LoginAccountRequest(
                    email,
                    "WrongPassword1!",
                    new DeviceInfo(DeviceType.WEB, "Chrome", "fp-1")
            );
            ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/login",
                    request,
                    ErrorResponse.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Nested
    class RefreshToken {

        @Test
        void shouldReturn200WithNewTokensWhenRefreshTokenValid() throws Exception {
            AuthTokenResponse tokens = registerAndVerifyEmail(email, PASSWORD);

            RefreshTokenRequest request = new RefreshTokenRequest(tokens.refreshToken());
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl() + "/api/auth/token/refresh",
                    request,
                    String.class
            );

            assertThat(response.getStatusCode())
                    .as("Refresh token response: %s", response.getBody())
                    .isEqualTo(HttpStatus.OK);
            AuthTokenResponse body = objectMapper.readValue(response.getBody(), AuthTokenResponse.class);
            assertThat(body).isNotNull();
            assertThat(body.accessToken()).isNotBlank();
            assertThat(body.refreshToken()).isNotBlank();
            assertThat(body.refreshToken()).isNotEqualTo(tokens.refreshToken());
        }
    }

    @Nested
    class Logout {

        @Test
        void shouldReturn204WhenLogoutWithValidRefreshToken() throws Exception {
            AuthTokenResponse tokens = registerAndVerifyEmail(email, PASSWORD);

            ResponseEntity<Void> response = restTemplate.exchange(
                    baseUrl() + "/api/auth/logout",
                    HttpMethod.POST,
                    new HttpEntity<>(new AuthApi.LogoutRequest(tokens.refreshToken())),
                    Void.class
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
    }

    private AuthTokenResponse registerAndVerifyEmail(String email, String password) throws Exception {
        restTemplate.postForEntity(
                baseUrl() + "/api/auth/register",
                new RegisterAccountRequest(email, password),
                RegisterAccountResponse.class
        );
        String otpCode = testOtpSender.getLastOtpCode().orElseThrow();
        ResponseEntity<String> verifyResponse = restTemplate.postForEntity(
                baseUrl() + "/api/auth/verify-email",
                new VerifyEmailRequest(email, otpCode, new DeviceInfo(DeviceType.WEB, "Chrome", "fp-1")),
                String.class
        );
        assertThat(verifyResponse.getStatusCode())
                .as("Verify email response: %s", verifyResponse.getBody())
                .isEqualTo(HttpStatus.OK);
        AuthTokenResponse body = objectMapper.readValue(verifyResponse.getBody(), AuthTokenResponse.class);
        assertThat(body).isNotNull();
        return body;
    }
}
