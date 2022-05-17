package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.AppRole;
import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.domain.Donor;
import io.tahayasin.blood_donor.model.AppUserDTO;
import io.tahayasin.blood_donor.model.DonorDTO;
import io.tahayasin.blood_donor.model.DonorRegistrationDTO;
import io.tahayasin.blood_donor.repos.AppRoleRepository;
import io.tahayasin.blood_donor.repos.AppUserRepository;
import io.tahayasin.blood_donor.repos.DonorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.tahayasin.blood_donor.twillio.SmsRequestDto;
import io.tahayasin.blood_donor.twillio.TwilioSmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class DonorService {

    private static final String MESSAGE = "_*Thank You! for showing interest toward noble cause.*_ \n\n" +
            "We Appreciate your willingness to donate blood.\n\n" + "*As a next step:*\n" +
            "Please confirm your email to continue your journey as a donor.\n\n" +
            "_Ignore if already done._";

    private static final Logger LOGGER = LoggerFactory.getLogger(DonorService.class);
    private final DonorRepository donorRepository;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final AppUserService appUserService;

    private final TwilioSmsSender twilioSmsSender;

    public DonorService(final DonorRepository donorRepository,
                        final AppUserRepository appUserRepository,
                        final AppRoleRepository appRoleRepository,
                        final AppUserService appUserService,
                        final TwilioSmsSender twilioSmsSender) {

        this.donorRepository = donorRepository;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.appUserService = appUserService;
        this.twilioSmsSender = twilioSmsSender;
    }

    public List<DonorDTO> findAll() {
        return donorRepository.findAll()
                .stream()
                .map(donor -> mapToDTO(donor, new DonorDTO()))
                .collect(Collectors.toList());
    }

    public DonorDTO get(final Long id) {
        return donorRepository.findById(id)
                .map(donor -> mapToDTO(donor, new DonorDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final DonorDTO donorDTO) {
        final Donor donor = new Donor();
        mapToEntity(donorDTO, donor);
        return donorRepository.save(donor).getId();
    }

    public void update(final Long id, final DonorDTO donorDTO) {
        final Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(donorDTO, donor);
        donorRepository.save(donor);
    }

    public void delete(final Long id) {
        donorRepository.deleteById(id);
    }

    private DonorDTO mapToDTO(final Donor donor, final DonorDTO donorDTO) {
        donorDTO.setId(donor.getId());
        donorDTO.setBloodGroup(donor.getBloodGroup());
        donorDTO.setLastDonationDate(donor.getLastDonationDate());
        donorDTO.setWhatsapp(donor.getWhatsapp());
        donorDTO.setStreetAddress(donor.getStreetAddress());
        donorDTO.setCity(donor.getCity());
        donorDTO.setState(donor.getState());
        donorDTO.setPincode(donor.getPincode());
        donorDTO.setUser(donor.getUser() == null ? null : donor.getUser().getId());
        return donorDTO;
    }

    private Donor mapToEntity(final DonorDTO donorDTO, final Donor donor) {
        donor.setBloodGroup(donorDTO.getBloodGroup());
        donor.setLastDonationDate(donorDTO.getLastDonationDate());
        donor.setWhatsapp(donorDTO.getWhatsapp());
        donor.setStreetAddress(donorDTO.getStreetAddress());
        donor.setCity(donorDTO.getCity());
        donor.setState(donorDTO.getState());
        donor.setPincode(donorDTO.getPincode());
        if (donorDTO.getUser() != null && (donor.getUser() == null || !donor.getUser().getId().equals(donorDTO.getUser()))) {
            final AppUser user = appUserRepository.findById(donorDTO.getUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
            donor.setUser(user);
        }
        return donor;
    }

    /*
    public Long register(final DonorRegistrationDTO donorRegistrationDTO) {
        LOGGER.info("New user attempting to register as donor");
        AppUserDTO appUserDTO = donorRegistrationDTO.getAppUserDTO();
        DonorDTO donorDTO = donorRegistrationDTO.getDonorDTO();
        Optional<AppRole> role = appRoleRepository.findByRoleName("ROLE_DONOR");

        if(!appUserRepository.findByUsername(appUserDTO.getUsername()).isPresent()) {
            LOGGER.info("Creating new user..");

            Long userId = appUserService.create(appUserDTO);
            Optional<AppUser> appUser = appUserRepository.findById(userId);
            appUser.get().getRoles().add(role.get());
            donorDTO.setUser(userId);
        }
        else {
            Optional<AppUser> appUser = appUserRepository.findByUsername(appUserDTO.getUsername());
            if(appUser.get().getRoles().contains(role.get()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account with username already exist");

            donorDTO.setUser(appUser.get().getId());
        }

        Long donorId = create(donorDTO);
        return donorId;
    }
     */

    /*public Long register(final DonorRegistrationDTO donorRegistrationDTO) {
        LOGGER.info("Attempting to register as donor");
        AppUserDTO appUserDTO = donorRegistrationDTO.getAppUserDTO();
        DonorDTO donorDTO = donorRegistrationDTO.getDonorDTO();
        Optional<AppRole> role = appRoleRepository.findByRoleName("ROLE_DONOR");

        Optional<Long> userId = appUserService.signup(appUserDTO);

        if(!userId.isPresent()) {
            Optional<AppUser> appUser = appUserRepository.findById(userId.get());
            appUser.get().getRoles().add(role.get());
            donorDTO.setUser(userId.get());
        }
        else {
            AppUser appUser = appUserRepository.findByUsername(appUserDTO.getUsername()).get();
            if(appUser.getRoles().contains(role.get()))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account with username already exist");
            else if(!appUser.isEnabled()) {
                throw new IllegalArgumentException("Email not confirmed, Confirm email and try again");
            }

            donorDTO.setUser(appUser.getId());
        }

        LOGGER.info("Registering donor");
        Long donorId = create(donorDTO);
        return donorId;
    }*/

    public Long register(final DonorRegistrationDTO donorRegistrationDTO) {
        LOGGER.info("Attempting to register as donor");
        AppUserDTO appUserDTO = donorRegistrationDTO.getAppUserDTO();
        DonorDTO donorDTO = donorRegistrationDTO.getDonorDTO();
        Optional<AppRole> role = appRoleRepository.findByRoleName("ROLE_DONOR");

        Optional<Long> userId = appUserService.signup(appUserDTO);

        AppUser appUser = appUserRepository.findByUsername(appUserDTO.getUsername()).get();
        if(appUser.getRoles().contains(role.get())) {
            if(appUser.isEnabled())
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account with username already exist");
            else
                return appUser.getDonor().getId();
        }


        appUser.getRoles().add(role.get());
        donorDTO.setUser(appUser.getId());


        LOGGER.info("Registering donor");
        Long donorId = create(donorDTO);

        LOGGER.info("Sending whatsapp message to donor at {}", donorDTO.getWhatsapp());
        SmsRequestDto smsRequestDto = new SmsRequestDto("+91" + donorDTO.getWhatsapp(), MESSAGE);
        twilioSmsSender.sendSms(smsRequestDto);

        return donorId;
    }

}
