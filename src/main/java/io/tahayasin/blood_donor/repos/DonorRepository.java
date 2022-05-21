package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    //Page<Donor> findByBloodGroupAndCityOrPincode(String bloodGroup, String city, String pincode, Pageable pageable);

    @Query("select d from Donor d where d.bloodGroup in :bloodGroupList and (d.city = :city or d.pincode = :pincode)")
    Page<Donor> findByBloodGroupAndCityOrPincode(List<String> bloodGroupList, String city, String pincode, Pageable pageable);
}
