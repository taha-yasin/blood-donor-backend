package io.tahayasin.blood_donor.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppRoleDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String roleName;

}
