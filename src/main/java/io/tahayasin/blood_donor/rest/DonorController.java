package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.domain.Donor;
import io.tahayasin.blood_donor.model.DonorDTO;
import io.tahayasin.blood_donor.model.DonorRegistrationDTO;
import io.tahayasin.blood_donor.service.DonorService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping(value = "/api/donors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DonorController {

    private final DonorService donorService;

    public DonorController(final DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping
    public ResponseEntity<List<DonorDTO>> getAllDonors() {
        return ResponseEntity.ok(donorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDTO> getDonor(@PathVariable final Long id) {
        return ResponseEntity.ok(donorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createDonor(@RequestBody @Valid final DonorDTO donorDTO) {
        return new ResponseEntity<>(donorService.create(donorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDonor(@PathVariable final Long id,
            @RequestBody @Valid final DonorDTO donorDTO) {
        donorService.update(id, donorDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable final Long id) {
        donorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> registerDonor(@RequestBody @Valid DonorRegistrationDTO donorRegistrationDTO) {

        return new ResponseEntity<>(donorService.register(donorRegistrationDTO), HttpStatus.CREATED);
    }

}
