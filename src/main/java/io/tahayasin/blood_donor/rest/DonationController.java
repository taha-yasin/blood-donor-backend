package io.tahayasin.blood_donor.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahayasin.blood_donor.model.DonationDTO;
import io.tahayasin.blood_donor.service.DonationService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("http://localhost:3000")
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

    @GetMapping("/requests")
    public ResponseEntity<List<DonationDTO>> sentRequest() {
        return ResponseEntity.ok(donationService.sentRequest());
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStatus(@RequestBody String requestId, String message) {
        donationService.updateStatus(requestId, message);
        return ResponseEntity.ok().build();
    }

}
