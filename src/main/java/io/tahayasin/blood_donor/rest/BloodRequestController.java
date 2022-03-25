package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.domain.Donor;
import io.tahayasin.blood_donor.model.BloodRequestDTO;
import io.tahayasin.blood_donor.model.DonorPageDTO;
import io.tahayasin.blood_donor.model.FindDonorDTO;
import io.tahayasin.blood_donor.service.BloodRequestService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping(value = "/api/bloodRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(final BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @GetMapping
    public ResponseEntity<List<BloodRequestDTO>> getAllBloodRequests() {
        return ResponseEntity.ok(bloodRequestService.findAll());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<BloodRequestDTO> getBloodRequest(@PathVariable final UUID requestId) {
        return ResponseEntity.ok(bloodRequestService.get(requestId));
    }

    @PostMapping
    public ResponseEntity<UUID> createBloodRequest(
            @RequestBody @Valid final BloodRequestDTO bloodRequestDTO) {
        return new ResponseEntity<>(bloodRequestService.create(bloodRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateBloodRequest(@PathVariable final UUID requestId,
            @RequestBody @Valid final BloodRequestDTO bloodRequestDTO) {
        bloodRequestService.update(requestId, bloodRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteBloodRequest(@PathVariable final UUID requestId) {
        bloodRequestService.delete(requestId);
        return ResponseEntity.noContent().build();
    }

        @GetMapping("/find-donor")
    @ResponseStatus(HttpStatus.OK)
    public FindDonorDTO findDonor(@RequestParam String bloodGroup,
                                  @RequestParam String city,
                                  @RequestParam String pincode,
                                  @RequestParam int pageNo) {
        Page<Donor> page = bloodRequestService.findDonor(bloodGroup,
                city,
                pincode,
                pageNo,
                10,
                "streetAddress");

        List<DonorPageDTO> donors = page.getContent().stream().map(DonorPageDTO::new).collect(Collectors.toList());

        FindDonorDTO donorsPage = new FindDonorDTO(page.getTotalPages(),
                page.getTotalElements(),
                donors);

        return  donorsPage;
    }

}
