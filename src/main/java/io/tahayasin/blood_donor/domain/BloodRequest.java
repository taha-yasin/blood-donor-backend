package io.tahayasin.blood_donor.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class BloodRequest {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID requestId;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String requiredBloodGroup;

    @Column(nullable = false)
    private String whatsapp;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private int unitsRequired;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String governmentId;

    @Column
    private LocalDateTime generatedAt;

    @Column
    private String status;

    @Column
    private Boolean isActive;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "donation_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "donor_id")
    )
    @JsonIgnore
    private Set<Donor> donors;

    @JsonIgnore
    @OneToMany(mappedBy = "bloodRequest")
    private Set<Donation> donations;

    @JsonIgnore
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id")
    private AppUser recipientUser;

}
