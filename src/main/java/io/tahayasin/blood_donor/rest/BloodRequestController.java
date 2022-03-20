package io.tahayasin.blood_donor.rest;

import io.tahayasin.blood_donor.model.BloodRequestDTO;
import io.tahayasin.blood_donor.service.BloodRequestService;
import java.util.List;
import java.util.UUID;
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

}
