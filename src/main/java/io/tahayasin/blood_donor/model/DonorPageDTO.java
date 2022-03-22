package io.tahayasin.blood_donor.model;

import io.tahayasin.blood_donor.domain.Donor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DonorPageDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String bloodGroup;
    private String streetAddress;

    public DonorPageDTO(Donor donor) {
        this.id = donor.getId();
        this.firstName = donor.getUser().getFirstName();
        this.lastName = donor.getUser().getLastName();
        this.age = donor.getUser().getAge();
        this.gender = donor.getUser().getGender();
        this.bloodGroup = donor.getBloodGroup();
        this.streetAddress = donor.getStreetAddress();
    }
}
