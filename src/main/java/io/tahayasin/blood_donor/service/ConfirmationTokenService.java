package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.ConfirmationToken;
import io.tahayasin.blood_donor.repos.AppUserRepository;
import io.tahayasin.blood_donor.repos.ConfirmationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ConfirmationTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationTokenService.class);
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;

    public ConfirmationTokenService(final ConfirmationTokenRepository confirmationTokenRepository,
                                    final AppUserRepository appUserRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.appUserRepository = appUserRepository;
    }

   /* public void saveConfirmationToken(ConfirmationToken token) {
        LOGGER.info("Saving confirmation token..");
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        LOGGER.info("Getting confirmation token..");
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        LOGGER.info("Setting token confirmation time..");
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }*/

    @Transactional
    public String confirmToken(String token) {
        LOGGER.info("Attempting to confirm ");
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        appUserRepository.enableAppUser(confirmationToken.getAppUser().getUsername());
        return "confirmed";
    }
}
