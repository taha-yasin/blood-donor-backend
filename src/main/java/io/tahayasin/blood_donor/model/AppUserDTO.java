package io.tahayasin.blood_donor.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppUserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    private String password;


    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    private LocalDate dateOfBirth;

    @Size(max = 255)
    private String gender;

    private List<Long> userRoles;

}
