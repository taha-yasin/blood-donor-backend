package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.AppRole;
import io.tahayasin.blood_donor.model.AppRoleDTO;
import io.tahayasin.blood_donor.repos.AppRoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AppRoleService {

    private final AppRoleRepository appRoleRepository;

    public AppRoleService(final AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public List<AppRoleDTO> findAll() {
        return appRoleRepository.findAll()
                .stream()
                .map(appRole -> mapToDTO(appRole, new AppRoleDTO()))
                .collect(Collectors.toList());
    }

    public AppRoleDTO get(final Long id) {
        return appRoleRepository.findById(id)
                .map(appRole -> mapToDTO(appRole, new AppRoleDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final AppRoleDTO appRoleDTO) {
        final AppRole appRole = new AppRole();
        mapToEntity(appRoleDTO, appRole);
        return appRoleRepository.save(appRole).getId();
    }

    public void update(final Long id, final AppRoleDTO appRoleDTO) {
        final AppRole appRole = appRoleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(appRoleDTO, appRole);
        appRoleRepository.save(appRole);
    }

    public void delete(final Long id) {
        appRoleRepository.deleteById(id);
    }

    private AppRoleDTO mapToDTO(final AppRole appRole, final AppRoleDTO appRoleDTO) {
        appRoleDTO.setId(appRole.getId());
        appRoleDTO.setRoleName(appRole.getRoleName());
        return appRoleDTO;
    }

    private AppRole mapToEntity(final AppRoleDTO appRoleDTO, final AppRole appRole) {
        appRole.setRoleName(appRoleDTO.getRoleName());
        return appRole;
    }

}
