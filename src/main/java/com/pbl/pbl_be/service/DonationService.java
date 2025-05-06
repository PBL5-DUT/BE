package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.model.Donation;

import java.util.List;

public interface DonationService {
    List<Donation> getDonationsByProjectId(Integer projectId);
    List<DonationDTO> getAllDonations();
}
