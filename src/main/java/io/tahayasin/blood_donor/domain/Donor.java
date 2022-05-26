package io.tahayasin.blood_donor.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donor {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String bloodGroup;

    @Column
    private LocalDate lastDonationDate;

    @Column
    private String whatsapp;

    @Column
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @Column
    private String state;

    @Column(nullable = false)
    private String pincode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @JsonIgnore
    @ManyToMany(mappedBy = "donors")
    private Set<BloodRequest> bloodRequests;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "donor")
    private Set<Donation> donations;

}
