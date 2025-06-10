package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<DonationDTO>> getDonationsByProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok( donationService.getDonationsByProjectId(projectId));
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @PostMapping
    public ResponseEntity<Void> createDonation(@RequestBody DonationDTO donationDTO) {
        System.out.println(donationDTO.getUserId());
        this.donationService.saveDonation(donationDTO);
        return ResponseEntity.ok().build();
    }
}
