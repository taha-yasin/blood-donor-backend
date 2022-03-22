package io.tahayasin.blood_donor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonorRegistrationDTO {
    private DonorDTO donorDTO;
    private AppUserDTO appUserDTO;
}
