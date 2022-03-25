package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.model.AppUserDTO;
import io.tahayasin.blood_donor.service.AppUserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping(value = "/api/appUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(final AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getAppUser(@PathVariable final Long id) {
        return ResponseEntity.ok(appUserService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAppUser(@RequestBody @Valid final AppUserDTO appUserDTO) {
        return new ResponseEntity<>(appUserService.create(appUserDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAppUser(@PathVariable final Long id,
            @RequestBody @Valid final AppUserDTO appUserDTO) {
        appUserService.update(id, appUserDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable final Long id) {
        appUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody @Valid AppUserDTO appUserDTO) {
        return ResponseEntity.ok(appUserService.signin(appUserDTO)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "Login Failed");
                }));
    }

    @PostMapping("/signup")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> signup(@RequestBody @Valid AppUserDTO appUserDTO) {

        return new ResponseEntity<>(appUserService.signup(appUserDTO)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "User already exists");
                }),
                HttpStatus.ACCEPTED);
    }

}
