package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.mapper.DonationMapper;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.repository.UserRepository; // This might not be needed in service impl if only mapper uses it
import com.pbl.pbl_be.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORT THIS

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;


    @Autowired
    private DonationMapper donationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DonationDTO> getDonationsByProjectId(Integer projectId) {

        List<Donation> donations = donationRepository.findByProjectId(projectId);
        if (donations != null && !donations.isEmpty()) {
            return  donations.stream()
                    .map(donation -> donationMapper.toDto(donation)) // Assuming 0 is the userId for the current user
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationDTO> getAllDonations() {

        List<Donation> donations = this.donationRepository.findAllWithUser();

        if (donations != null && !donations.isEmpty()) {
            return donations.stream()
                    .map(donation -> donationMapper.toDto(donation)) // Assuming 0 is the userId for the current user
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true) // This method is also a read operation
    public List<DonationStatsDTO> donationsByProjectAndDate() {
        return donationRepository.sumDonationsByProjectAndDate();
    }

    @Override
    @Transactional
    public void saveDonation(DonationDTO donationDTO) {
        System.out.println(donationDTO.getUserId());
        Donation donation = donationMapper.toEntity(donationDTO);
        donation.setCreatedAt(LocalDateTime.now());
        this.donationRepository.save(donation);
    }
}