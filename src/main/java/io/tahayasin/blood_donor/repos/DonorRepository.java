package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonorRepository extends JpaRepository<Donor, Long> {

    //Page<Donor> findByBloodGroupAndCityOrPincode(String bloodGroup, String city, String pincode, Pageable pageable);
    Page<Donor> findByBloodGroupAndCityOrPincode(String bloodGroup, String city, String pincode, Pageable pageable);
}
