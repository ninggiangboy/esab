package dev.ngb.app.identity.application.usecase.password.reset_password;

import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.password.reset_password.dto.ResetPasswordRequest;
import dev.ngb.application.UseCaseService;
import dev.ngb.domain.identity.error.AccountError;
import dev.ngb.domain.identity.model.account.Account;
import dev.ngb.domain.identity.model.otp.AccountOtp;
import dev.ngb.domain.identity.model.otp.OtpPurpose;
import dev.ngb.domain.identity.model.session.AccountSession;
import dev.ngb.domain.identity.repository.AccountOtpRepository;
import dev.ngb.domain.identity.repository.AccountRepository;
import dev.ngb.domain.identity.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ResetPasswordUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(ResetPasswordRequest request) {
        log.info("Reset password attempt for email={}", request.email() != null ? request.email().replaceAll("(?<=.).(?=.*@)", "*") : "***");

        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Reset password failed: account not found");
                    return AccountError.ACCOUNT_NOT_FOUND.exception();
                });

        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(account.getId(), OtpPurpose.PASSWORD_RESET)
                .orElseThrow(() -> {
                    log.warn("Reset password failed: no active OTP for accountId={}", account.getId());
                    return AccountError.INVALID_OTP.exception();
                });

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);
        log.debug("Password reset OTP verified for accountId={}", account.getId());

        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        account.changePassword(newPasswordHash);
        accountRepository.save(account);

        List<AccountSession> activeSessions = accountSessionRepository.findActiveByAccountId(account.getId());
        activeSessions.forEach(AccountSession::revoke);
        accountSessionRepository.saveAll(activeSessions);
        log.info("Reset password successful accountId={}, revoked {} session(s)", account.getId(), activeSessions.size());
    }
}
