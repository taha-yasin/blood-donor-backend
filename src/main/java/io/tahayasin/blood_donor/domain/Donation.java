package io.tahayasin.blood_donor.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Donation {

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
    private Long donationId;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_request_id")
    private BloodRequest bloodRequest;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_id")
    private Donor donor;

}

//package io.tahayasin.blood_donor.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//public class Donation {
//    @Id
//    @Column(nullable = false, updatable = false)
//    @SequenceGenerator(
//            name = "primary_sequence",
//            sequenceName = "primary_sequence",
//            allocationSize = 1,
//            initialValue = 10000
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "primary_sequence"
//    )
//    private Long donationId;
//
//    @ManyToOne
//    @JoinColumn(name = "request_id")
//    BloodRequest bloodRequest;
//
//    @ManyToOne
//    @JoinColumn(name = "donor_id")
//    Donor donor;
//
//    @Column
//    private String Status;
//}
