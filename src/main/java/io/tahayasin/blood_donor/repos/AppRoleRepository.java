package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
}
