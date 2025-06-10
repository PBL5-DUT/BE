package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ReportService reportService;

    @PostMapping()
    public ResponseEntity<Void> createReport(@RequestHeader("Authorization") String token, @RequestBody ReportDTO reportDTO) {
        int userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));

        this.reportService.addReport(reportDTO, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/pending")
    public ResponseEntity<List<ReportDTO>> getPendingReports(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String type) {

        return null;
    }
    @PutMapping("/resolve/{reportId}")
    public ResponseEntity<Void> resolveReport(
            @RequestHeader("Authorization") String token,
            @PathVariable int reportId) {

        return null;
    }
    @PutMapping("/dismiss/{reportId}")
    public ResponseEntity<Void> dismissReport(
            @RequestHeader("Authorization") String token,
            @PathVariable int reportId) {

        return null;
    }
}
