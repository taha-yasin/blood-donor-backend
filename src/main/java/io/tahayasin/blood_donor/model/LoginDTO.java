package io.tahayasin.blood_donor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String name;
    private String bloodGroup;
    private String token;
}
