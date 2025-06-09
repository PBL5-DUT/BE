package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.model.Donation;

import java.util.List;

public interface DonationService {
    List<DonationDTO> getDonationsByProjectId(Integer projectId);
    List<DonationDTO> getAllDonations();

    List<DonationStatsDTO> donationsByProjectAndDate();
    void saveDonation(DonationDTO donationDTO);

}
