package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.ConfirmationToken;
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

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
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
    }
}
