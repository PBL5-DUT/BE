package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DonationMapper {

    @Autowired
    private UserRepository userRepository;

    public DonationDTO toDto(Donation donation) {
        if (donation == null) {
            return null;
        }
        DonationDTO dto= new DonationDTO();
        dto.setDonationId(donation.getDonationId());
        dto.setUserId(donation.getUser().getUserId());
        dto.setType(donation.getType());
        dto.setUserName(donation.getUser().getUsername());
        dto.setTxnRef(donation.getTxnRef());
        dto.setGoodDescription(donation.getGoodDescription());
        dto.setProjectId(donation.getProjectId());
        dto.setType(donation.getType());
        dto.setAmount(donation.getAmount());
        dto.setCreatedAt(donation.getCreatedAt());
        return dto;
    }

    public Donation toEntity(DonationDTO dto) {
        if (dto == null) {
            return null;
        }
        Donation donation = new Donation();
        donation.setDonationId(dto.getDonationId());
        if (dto.getUserId() != null) {
            donation.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + dto.getUserId())));
        } else {
            donation.setUser(null);
        }
        donation.setType(dto.getType());
        donation.setTxnRef(dto.getTxnRef());
        donation.setGoodDescription(dto.getGoodDescription());
        donation.setProjectId(dto.getProjectId());
        donation.setAmount(dto.getAmount());
        donation.setType(dto.getType());
        donation.setCreatedAt(dto.getCreatedAt());
        return donation;
    }
}