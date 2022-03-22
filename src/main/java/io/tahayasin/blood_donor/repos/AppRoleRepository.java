package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByRoleName(String roleName);
}
