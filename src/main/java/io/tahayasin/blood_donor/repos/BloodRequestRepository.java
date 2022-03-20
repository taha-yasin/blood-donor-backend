package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.BloodRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {
}
