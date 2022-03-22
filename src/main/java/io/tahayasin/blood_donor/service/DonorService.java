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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final AppUserRepository appUserRepository;

    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;

    public DonorService(final DonorRepository donorRepository,
                        final AppUserRepository appUserRepository,
                        final AppRoleRepository appRoleRepository,
                        final PasswordEncoder passwordEncoder,
                        final AppUserService appUserService) {
        this.donorRepository = donorRepository;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
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

    public Long register(final DonorRegistrationDTO donorRegistrationDTO) {
        //LOGGER.info("New user attempting to sign in");
        AppUserDTO appUserDTO = donorRegistrationDTO.getAppUserDTO();
        DonorDTO donorDTO = donorRegistrationDTO.getDonorDTO();
        if(!appUserRepository.findByUsername(appUserDTO.getUsername()).isPresent()) {
            Long userId = appUserService.create(appUserDTO);
            AppUser appUser = appUserRepository.findById(userId).get();
            AppRole role_donor = appRoleRepository.findByRoleName("ROLE_DONOR").get();
            appUser.getRoles().add(role_donor);

            donorDTO.setUser(userId);
        }
        else {
            AppUser appUser = appUserRepository.findByUsername(appUserDTO.getUsername()).get();
            donorDTO.setUser(appUser.getId());
        }

        Long donorId = create(donorDTO);
        return donorId;
    }

}
