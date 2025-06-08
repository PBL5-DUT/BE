package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
