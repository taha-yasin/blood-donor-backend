package io.tahayasin.blood_donor.model;

import java.util.UUID;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DonationDTO {

    private Long donationId;

    @Size(max = 255)
    private String status;

    private String donorName;
    private String bloodGroup;
    private String gender;
    private String streetAddress;

    private UUID bloodRequest;

    private Long donor;

}