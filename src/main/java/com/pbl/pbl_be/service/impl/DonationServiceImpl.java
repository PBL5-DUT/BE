package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;


    @Override
    public List<Donation> getDonationsByProjectId(Integer projectId) {
        return donationRepository.findByProjectId(projectId);
    }
}
