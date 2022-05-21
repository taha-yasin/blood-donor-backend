package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.domain.BloodRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {
    List<BloodRequest> findAllByDonors(Donor donor);
    List<BloodRequest> findAllByRecipientUser(AppUser appUser);

    @Transactional
    @Modifying
    @Query("UPDATE BloodRequest req " +
            "SET req.status = ?2 " +
            "WHERE req.requestId = ?1")
    int updateStatus(UUID requestId, String message);
}
