package io.tahayasin.blood_donor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindDonorDTO {
    private int numberOfPages;
    private Long numberOfElements;
    private List<DonorPageDTO> donors;
}
