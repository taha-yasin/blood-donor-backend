package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonorRepository extends JpaRepository<Donor, Long> {
}
