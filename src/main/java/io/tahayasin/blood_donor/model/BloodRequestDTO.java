package io.tahayasin.blood_donor.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BloodRequestDTO {

    private UUID requestId;

    @NotNull
    @Size(max = 255)
    private String patientName;

    @NotNull
    @Size(max = 255)
    private String requiredBloodGroup;

    @NotNull
    @Size(max = 255)
    private String whatsapp;

    @NotNull
    @Size(max = 255)
    private String streetAddress;

    @NotNull
    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String governmentId;

    private LocalDateTime generatedAt;

    @Size(max = 255)
    private String status;

    private Boolean isActive;

    private List<Long> donors;

    private Long recipient;

}
