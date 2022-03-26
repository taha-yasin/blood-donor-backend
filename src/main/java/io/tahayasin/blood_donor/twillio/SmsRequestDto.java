package io.tahayasin.blood_donor.twillio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SmsRequestDto {

    @NotBlank
    private final String phoneNumber; // destination

    @NotBlank
    private final String message;

    public SmsRequestDto(@JsonProperty("phoneNumber") String phoneNumber,
                         @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }
}