package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    @Query("SELECT user.id FROM AppUser user WHERE user.username = :name")
    Long findIdByName(@Param("name") String name);
}
