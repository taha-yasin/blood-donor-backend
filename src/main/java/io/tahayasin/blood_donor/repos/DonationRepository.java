package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.BloodRequest;
import io.tahayasin.blood_donor.domain.Donation;
import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByBloodRequest(BloodRequest request);

    @Transactional
    @Modifying
    @Query("UPDATE Donation d " +
            "SET d.status = ?2 " +
            //"WHERE d.donor = ?1"
            "WHERE d.bloodRequest = ?1" +
            "AND d.donor = ?2"
    )
    int updateStatus(BloodRequest request, Donor donor, String message);


}