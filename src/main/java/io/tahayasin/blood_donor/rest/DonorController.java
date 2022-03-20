package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.model.DonorDTO;
import io.tahayasin.blood_donor.service.DonorService;
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

}
