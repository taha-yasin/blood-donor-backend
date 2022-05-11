package io.tahayasin.blood_donor;

import io.tahayasin.blood_donor.domain.AppRole;
import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.repos.AppRoleRepository;
import io.tahayasin.blood_donor.repos.AppUserRepository;
import io.tahayasin.blood_donor.repos.BloodRequestRepository;
import io.tahayasin.blood_donor.repos.DonorRepository;
import io.tahayasin.blood_donor.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;


@SpringBootApplication
public class BloodDonorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloodDonorApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserRepository appUserRepository,
                          AppRoleRepository appRoleRepository,
                          DonorRepository donorRepository,
                          BloodRequestRepository bloodRequestRepository) {
        return args -> {
//            appRoleRepository.save(new AppRole(null, "ROLE_ADMIN", new HashSet<>()));
//            appRoleRepository.save(new AppRole(null, "ROLE_USER", new HashSet<>()));
//            appRoleRepository.save(new AppRole(null, "ROLE_DONOR", new HashSet<>()));
        };
    }
}
