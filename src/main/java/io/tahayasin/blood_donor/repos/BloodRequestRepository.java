package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.BloodRequest;

import java.util.List;
import java.util.UUID;

import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {
    List<BloodRequest> findAllByDonors(Donor donor);
}
