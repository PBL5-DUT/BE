package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping("/project/{projectId}")
    public List<Donation> getDonationsByProject(@PathVariable Integer projectId) {
        return donationService.getDonationsByProjectId(projectId);
    }
}
