package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.AppUser;
import io.tahayasin.blood_donor.domain.BloodRequest;
import io.tahayasin.blood_donor.domain.Donor;
import io.tahayasin.blood_donor.model.BloodRequestDTO;
import io.tahayasin.blood_donor.repos.AppUserRepository;
import io.tahayasin.blood_donor.repos.BloodRequestRepository;
import io.tahayasin.blood_donor.repos.DonorRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final DonorRepository donorRepository;
    private final AppUserRepository appUserRepository;

    public BloodRequestService(final BloodRequestRepository bloodRequestRepository,
            final DonorRepository donorRepository, final AppUserRepository appUserRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.donorRepository = donorRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<BloodRequestDTO> findAll() {
        return bloodRequestRepository.findAll()
                .stream()
                .map(bloodRequest -> mapToDTO(bloodRequest, new BloodRequestDTO()))
                .collect(Collectors.toList());
    }

    public BloodRequestDTO get(final UUID requestId) {
        return bloodRequestRepository.findById(requestId)
                .map(bloodRequest -> mapToDTO(bloodRequest, new BloodRequestDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final BloodRequestDTO bloodRequestDTO) {
        final BloodRequest bloodRequest = new BloodRequest();
        mapToEntity(bloodRequestDTO, bloodRequest);
        return bloodRequestRepository.save(bloodRequest).getRequestId();
    }

    public void update(final UUID requestId, final BloodRequestDTO bloodRequestDTO) {
        final BloodRequest bloodRequest = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(bloodRequestDTO, bloodRequest);
        bloodRequestRepository.save(bloodRequest);
    }

    public void delete(final UUID requestId) {
        bloodRequestRepository.deleteById(requestId);
    }

    private BloodRequestDTO mapToDTO(final BloodRequest bloodRequest,
            final BloodRequestDTO bloodRequestDTO) {
        bloodRequestDTO.setRequestId(bloodRequest.getRequestId());
        bloodRequestDTO.setPatientName(bloodRequest.getPatientName());
        bloodRequestDTO.setRequiredBloodGroup(bloodRequest.getRequiredBloodGroup());
        bloodRequestDTO.setWhatsAppNumber(bloodRequest.getWhatsAppNumber());
        bloodRequestDTO.setStreetAddress(bloodRequest.getStreetAddress());
        bloodRequestDTO.setCity(bloodRequest.getCity());
        bloodRequestDTO.setGovernmentId(bloodRequest.getGovernmentId());
        bloodRequestDTO.setGeneratedAt(bloodRequest.getGeneratedAt());
        bloodRequestDTO.setStatus(bloodRequest.getStatus());
        bloodRequestDTO.setIsActive(bloodRequest.getIsActive());
        bloodRequestDTO.setDonationRequests(bloodRequest.getDonors() == null ? null : bloodRequest.getDonors().stream()
                .map(donor -> donor.getId())
                .collect(Collectors.toList()));
        bloodRequestDTO.setRecipient(bloodRequest.getRecipient() == null ? null : bloodRequest.getRecipient().getId());
        return bloodRequestDTO;
    }

    private BloodRequest mapToEntity(final BloodRequestDTO bloodRequestDTO,
            final BloodRequest bloodRequest) {
        bloodRequest.setPatientName(bloodRequestDTO.getPatientName());
        bloodRequest.setRequiredBloodGroup(bloodRequestDTO.getRequiredBloodGroup());
        bloodRequest.setWhatsAppNumber(bloodRequestDTO.getWhatsAppNumber());
        bloodRequest.setStreetAddress(bloodRequestDTO.getStreetAddress());
        bloodRequest.setCity(bloodRequestDTO.getCity());
        bloodRequest.setGovernmentId(bloodRequestDTO.getGovernmentId());
        bloodRequest.setGeneratedAt(bloodRequestDTO.getGeneratedAt());
        bloodRequest.setStatus(bloodRequestDTO.getStatus());
        bloodRequest.setIsActive(bloodRequestDTO.getIsActive());
        if (bloodRequestDTO.getDonationRequests() != null) {
            final List<Donor> donationRequests = donorRepository.findAllById(bloodRequestDTO.getDonationRequests());
            if (donationRequests.size() != bloodRequestDTO.getDonationRequests().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of donationRequests not found");
            }
            bloodRequest.setDonors(donationRequests.stream().collect(Collectors.toSet()));
        }
        if (bloodRequestDTO.getRecipient() != null && (bloodRequest.getRecipient() == null || !bloodRequest.getRecipient().getId().equals(bloodRequestDTO.getRecipient()))) {
            final AppUser recipient = appUserRepository.findById(bloodRequestDTO.getRecipient())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipient not found"));
            bloodRequest.setRecipient(recipient);
        }
        return bloodRequest;
    }

}
