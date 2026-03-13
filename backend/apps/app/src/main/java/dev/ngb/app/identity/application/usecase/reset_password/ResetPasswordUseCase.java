package dev.ngb.app.identity.application.usecase.reset_password;

import dev.ngb.app.identity.application.port.PasswordEncoder;
import dev.ngb.app.identity.application.usecase.reset_password.dto.ResetPasswordRequest;
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

import java.util.List;

@RequiredArgsConstructor
public class ResetPasswordUseCase implements UseCaseService {

    private final AccountRepository accountRepository;
    private final AccountOtpRepository accountOtpRepository;
    private final AccountSessionRepository accountSessionRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(ResetPasswordRequest request) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(AccountError.ACCOUNT_NOT_FOUND::exception);

        AccountOtp otp = accountOtpRepository
                .findLatestActiveByAccountIdAndPurpose(account.getId(), OtpPurpose.PASSWORD_RESET)
                .orElseThrow(AccountError.INVALID_OTP::exception);

        otp.verify(request.otpCode());
        accountOtpRepository.save(otp);

        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        account.changePassword(newPasswordHash);
        accountRepository.save(account);

        List<AccountSession> activeSessions = accountSessionRepository.findActiveByAccountId(account.getId());
        activeSessions.forEach(AccountSession::revoke);
        accountSessionRepository.saveAll(activeSessions);
    }
}
