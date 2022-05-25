package io.tahayasin.blood_donor.service;

import io.tahayasin.blood_donor.domain.BloodRequest;
import io.tahayasin.blood_donor.domain.Donation;
import io.tahayasin.blood_donor.domain.Donor;
import io.tahayasin.blood_donor.model.DonationDTO;
import io.tahayasin.blood_donor.repos.BloodRequestRepository;
import io.tahayasin.blood_donor.repos.DonationRepository;
import io.tahayasin.blood_donor.repos.DonorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final DonorRepository donorRepository;

    public DonationService(final DonationRepository donationRepository,
                           final BloodRequestRepository bloodRequestRepository,
                           final DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.donorRepository = donorRepository;
    }

    public List<DonationDTO> findAll() {
        return donationRepository.findAll()
                .stream()
                .map(donation -> mapToDTO(donation, new DonationDTO()))
                .collect(Collectors.toList());
    }

    public DonationDTO get(final Long donationId) {
        return donationRepository.findById(donationId)
                .map(donation -> mapToDTO(donation, new DonationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final DonationDTO donationDTO) {
        final Donation donation = new Donation();
        mapToEntity(donationDTO, donation);
        return donationRepository.save(donation).getDonationId();
    }

    public void update(final Long donationId, final DonationDTO donationDTO) {
        final Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(donationDTO, donation);
        donationRepository.save(donation);
    }

    public void delete(final Long donationId) {
        donationRepository.deleteById(donationId);
    }

    private DonationDTO mapToDTO(final Donation donation, final DonationDTO donationDTO) {
        donationDTO.setDonationId(donation.getDonationId());
        donationDTO.setStatus(donation.getStatus());
        donationDTO.setBloodRequest(donation.getBloodRequest() == null ? null : donation.getBloodRequest().getRequestId());
        donationDTO.setDonor(donation.getDonor() == null ? null : donation.getDonor().getId());
        return donationDTO;
    }

    private Donation mapToEntity(final DonationDTO donationDTO, final Donation donation) {
        donation.setStatus(donationDTO.getStatus());
        if (donationDTO.getBloodRequest() != null && (donation.getBloodRequest() == null || !donation.getBloodRequest().getRequestId().equals(donationDTO.getBloodRequest()))) {
            final BloodRequest bloodRequest = bloodRequestRepository.findById(donationDTO.getBloodRequest())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bloodRequest not found"));
            donation.setBloodRequest(bloodRequest);
        }
        if (donationDTO.getDonor() != null && (donation.getDonor() == null || !donation.getDonor().getId().equals(donationDTO.getDonor()))) {
            final Donor donor = donorRepository.findById(donationDTO.getDonor())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "donor not found"));
            donation.setDonor(donor);
        }
        return donation;
    }

}