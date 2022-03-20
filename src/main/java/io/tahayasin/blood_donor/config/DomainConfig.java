package io.tahayasin.blood_donor.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.tahayasin.blood_donor.domain")
@EnableJpaRepositories("io.tahayasin.blood_donor.repos")
@EnableTransactionManagement
public class DomainConfig {
}
