package io.tahayasin.blood_donor.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahayasin.blood_donor.model.DonationDTO;
import io.tahayasin.blood_donor.service.DonationService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/donations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DonationController {

    private final DonationService donationService;

    public DonationController(final DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getAllDonations() {
        return ResponseEntity.ok(donationService.findAll());
    }

    @GetMapping("/{donationId}")
    public ResponseEntity<DonationDTO> getDonation(@PathVariable final Long donationId) {
        return ResponseEntity.ok(donationService.get(donationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDonation(@RequestBody @Valid final DonationDTO donationDTO) {
        return new ResponseEntity<>(donationService.create(donationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{donationId}")
    public ResponseEntity<Void> updateDonation(@PathVariable final Long donationId,
                                               @RequestBody @Valid final DonationDTO donationDTO) {
        donationService.update(donationId, donationDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{donationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDonation(@PathVariable final Long donationId) {
        donationService.delete(donationId);
        return ResponseEntity.noContent().build();
    }

}
