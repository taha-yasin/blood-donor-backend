package io.tahayasin.blood_donor.repos;

import io.tahayasin.blood_donor.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    @Query("SELECT user.id FROM AppUser user WHERE user.username = :name")
    Long findIdByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser user " +
            "SET user.enabled = TRUE WHERE user.username = ?1")
    int enableAppUser(String username);
}
