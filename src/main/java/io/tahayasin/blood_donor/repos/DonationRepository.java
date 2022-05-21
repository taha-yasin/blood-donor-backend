package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonationRepository extends JpaRepository<Donation, Long> {
}