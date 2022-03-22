package io.tahayasin.blood_donor.model;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DonorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String bloodGroup;

    private LocalDate lastDonationDate;

    @NotNull
    @Size(max = 255)
    private String whatsapp;

    @Size(max = 255)
    private String streetAddress;

    @NotNull
    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String state;

    @NotNull
    private String pincode;

    private Long user;

}
