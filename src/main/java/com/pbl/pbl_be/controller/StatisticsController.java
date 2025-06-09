package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private DonationService donationService;

    @GetMapping("/donations-per-day")
    public ResponseEntity<List<DonationStatsDTO>> getDonationStats() {
        return ResponseEntity.ok(donationService.donationsByProjectAndDate());
    }
}
