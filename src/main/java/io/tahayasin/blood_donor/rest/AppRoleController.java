package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.model.AppRoleDTO;
import io.tahayasin.blood_donor.service.AppRoleService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/appRoles", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppRoleController {

    private final AppRoleService appRoleService;

    public AppRoleController(final AppRoleService appRoleService) {
        this.appRoleService = appRoleService;
    }

    @GetMapping
    public ResponseEntity<List<AppRoleDTO>> getAllAppRoles() {
        return ResponseEntity.ok(appRoleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppRoleDTO> getAppRole(@PathVariable final Long id) {
        return ResponseEntity.ok(appRoleService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAppRole(@RequestBody @Valid final AppRoleDTO appRoleDTO) {
        return new ResponseEntity<>(appRoleService.create(appRoleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAppRole(@PathVariable final Long id,
            @RequestBody @Valid final AppRoleDTO appRoleDTO) {
        appRoleService.update(id, appRoleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppRole(@PathVariable final Long id) {
        appRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
