package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.AppRole;
import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.domain.ConfirmationToken;
import io.tahayasin.blood_donor.repos.AppRoleRepository;
import io.tahayasin.blood_donor.repos.AppUserRepository;
import io.tahayasin.blood_donor.repos.ConfirmationTokenRepository;
//import io.tahayasin.blood_donor.twillio.SmsRequestDto;
//import io.tahayasin.blood_donor.twillio.TwilioSmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ConfirmationTokenService {

    private static final String MESSAGE_DONOR = "*Email Confirmed:* \n\nYou have been successfully registered as a donor.";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationTokenService.class);
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

//    private final TwilioSmsSender twilioSmsSender;

    public ConfirmationTokenService(final ConfirmationTokenRepository confirmationTokenRepository,
                                    final AppUserRepository appUserRepository,
                                    final AppRoleRepository appRoleRepository
                                    ) {
//        final TwilioSmsSender twilioSmsSender
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
//        this.twilioSmsSender = twilioSmsSender;
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

        sendMessage(confirmationToken);
        return "confirmed";
    }

    public void sendMessage(ConfirmationToken confirmationToken) {
        AppRole role_donor = appRoleRepository.findByRoleName("ROLE_DONOR").get();
        AppRole role_user = appRoleRepository.findByRoleName("ROLE_USER").get();
        AppUser appUser = confirmationToken.getAppUser();

        String whatsapp = appUser.getDonor().getWhatsapp();

//        if(appUser.getRoles().contains(role_donor)) {
//            SmsRequestDto smsRequestDto = new SmsRequestDto("+91" + whatsapp, MESSAGE_DONOR);
//            twilioSmsSender.sendSms(smsRequestDto);
//        }

    }
}
