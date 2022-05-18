package io.tahayasin.blood_donor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String name;
    private String bloodGroup;
    private int age;
    private String mobile;
    private String address;
    private String email;
    private String token;
}
