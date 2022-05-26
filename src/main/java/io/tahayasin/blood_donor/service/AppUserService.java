package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.AppRole;
import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.domain.BloodRequest;
import io.tahayasin.blood_donor.domain.ConfirmationToken;
import io.tahayasin.blood_donor.model.AppUserDTO;
import io.tahayasin.blood_donor.repos.AppRoleRepository;
import io.tahayasin.blood_donor.repos.AppUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import io.tahayasin.blood_donor.repos.BloodRequestRepository;
import io.tahayasin.blood_donor.repos.ConfirmationTokenRepository;
import io.tahayasin.blood_donor.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class AppUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final HttpServletRequest request;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final BloodRequestRepository bloodRequestRepository;

    public AppUserService(final AppUserRepository appUserRepository,
                          final AppRoleRepository appRoleRepository,
                          final PasswordEncoder passwordEncoder,
                          final AuthenticationManager authenticationManager,
                          final JwtProvider jwtProvider,
                          final HttpServletRequest request,
                          final ConfirmationTokenRepository confirmationTokenRepository,
                          final EmailService emailService,
                          final BloodRequestRepository bloodRequestRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.request = request;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    public List<AppUserDTO> findAll() {
        return appUserRepository.findAll()
                .stream()
                .map(appUser -> mapToDTO(appUser, new AppUserDTO()))
                .collect(Collectors.toList());
    }

    public AppUserDTO get(final Long id) {
        return appUserRepository.findById(id)
                .map(appUser -> mapToDTO(appUser, new AppUserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public AppUserDTO getByUsername(final String username) {
        return appUserRepository.findByUsername(username)
                .map(appUser -> mapToDTO(appUser, new AppUserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final AppUserDTO appUserDTO) {
        final AppUser appUser = new AppUser();
        mapToEntity(appUserDTO, appUser);
        return appUserRepository.save(appUser).getId();
    }

    public void update(final Long id, final AppUserDTO appUserDTO) {
        final AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(appUserDTO, appUser);
        appUserRepository.save(appUser);
    }

    public void delete(final Long id) {
        appUserRepository.deleteById(id);
    }

    private AppUserDTO mapToDTO(final AppUser appUser, final AppUserDTO appUserDTO) {
        appUserDTO.setId(appUser.getId());
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setPassword(appUser.getPassword());
        appUserDTO.setFirstName(appUser.getFirstName());
        appUserDTO.setLastName(appUser.getLastName());
        appUserDTO.setDateOfBirth(appUser.getDataOfBirth());
        appUserDTO.setGender(appUser.getGender());
        if(appUser.getDonor() != null)
        {
            appUserDTO.setBloodGroup(appUser.getDonor().getBloodGroup());
            appUserDTO.setState(appUser.getDonor().getState());
            appUserDTO.setStreetAddress(appUser.getDonor().getStreetAddress());
            appUserDTO.setCity(appUser.getDonor().getCity());
            appUserDTO.setMobile(appUser.getDonor().getWhatsapp());
        }
        else{
            appUserDTO.setBloodGroup(null);
            appUserDTO.setState(appUser.getRequests().stream()
                    .map(state -> state.getStreetAddress())
                    .limit(1)
                    .collect(Collectors.joining()));
            appUserDTO.setCity(appUser.getRequests().stream()
                    .map(city -> city.getCity())
                    .limit(1)
                    .collect(Collectors.joining()));
            appUserDTO.setMobile(appUser.getRequests().stream()
                    .map(mobile -> mobile.getWhatsapp())
                    .limit(1)
                    .collect(Collectors.joining()));
        }
        appUserDTO.setUserRoles(appUser.getRoles() == null ? null : appUser.getRoles().stream()
                .map(appRole -> appRole.getId())
                .collect(Collectors.toList()));
        return appUserDTO;
    }

    private AppUser mapToEntity(final AppUserDTO appUserDTO, final AppUser appUser) {
        appUser.setUsername(appUserDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setDataOfBirth(appUserDTO.getDateOfBirth());
        appUser.setGender(appUserDTO.getGender());
        appUser.setEnabled(false); //TODO: change to false to enable email verification
        if (appUserDTO.getUserRoles() != null) {
            final List<AppRole> userRoles = appRoleRepository.findAllById(appUserDTO.getUserRoles());
            if (userRoles.size() != appUserDTO.getUserRoles().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of userRoles not found");
            }
            appUser.setRoles(userRoles.stream().collect(Collectors.toSet()));
        }
        else {
            Optional<AppRole> role = appRoleRepository.findByRoleName("ROLE_USER");
            appUser.getRoles().add(role.get());
        }
        return appUser;
    }

    public Optional<String> signin(final AppUserDTO appUserDTO) {
        LOGGER.info("New user attempting to signin");
        Optional<String> token = Optional.empty();
        Optional<AppUser> user = appUserRepository.findByUsername(appUserDTO.getUsername());
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserDTO.getUsername(), appUserDTO.getPassword()));
                token = Optional.of(jwtProvider.createToken(appUserDTO.getUsername(), user.get().getRoles()));
            } catch (AuthenticationException exception) {
                LOGGER.info("Log in failed for user {}", appUserDTO.getUsername());
            }
        }
        return token;
    }

    /*public Optional<Long> signup(final AppUserDTO appUserDTO) {
        LOGGER.info("New user attempting to signup");
        Optional<Long> userId = Optional.empty();
        if (!appUserRepository.findByUsername(appUserDTO.getUsername()).isPresent()) {
            Optional<AppRole> role = appRoleRepository.findByRoleName("ROLE_USER");
            userId = Optional.of(create(appUserDTO));
        }
        return userId;
    }*/

    public Optional<Long> signup(final AppUserDTO appUserDTO) {
        LOGGER.info("Attempting to signup");
        Optional<Long> userId = Optional.empty();
        final Long defaultUserId = -1L;

        if (!appUserRepository.findByUsername(appUserDTO.getUsername()).isPresent()) {
            LOGGER.info("Creating new user");
            userId = Optional.of(create(appUserDTO));
        }
        AppUser appUser = appUserRepository.findByUsername(appUserDTO.getUsername()).get();
        if(!appUser.isEnabled()) {
            LOGGER.info("Sending Confirmation mail");
            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    appUser
            );

            confirmationTokenRepository.save(confirmationToken);

//            String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
            String link = "http://localhost:3000/registration/verification/" + token;
            String toEmail = appUserDTO.getUsername();    //email of the user is used as the username
            String emailBody = emailService.buildEmail(appUserDTO.getFirstName(), link);
            emailService.send(toEmail, emailBody);

            userId = Optional.of(defaultUserId);
        }

        return userId;
    }

    /**
     * Deprecated: No more needed, can be safely removed
     * @return userId of User currenly logged in.
     */
    @Deprecated
    public Optional<Long> getAuthenticatedUserId() {
        LOGGER.info("Getting userId of user currently logged in");
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        Optional<Long> userId = Optional.empty();
        Optional<String> token = Optional.empty();
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = Optional.of(authHeader.replace("Bearer", "").trim());
        }

        String username = jwtProvider.getUsername(token.get());
        userId = Optional.of(appUserRepository.findIdByName(username));

        return userId;
    }

    public List<BloodRequest> sentRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserRepository.findByUsername(authentication.getName()).get();
        LOGGER.info("Searching for sent requests...");
        return bloodRequestRepository.findAllByRecipientUser(appUser);
    }

}
