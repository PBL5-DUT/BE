package com.pbl.pbl_be.controller;


import com.pbl.pbl_be.dto.CommentDTO;
import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.model.Report;

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

    @GetMapping("/postpending/{projectId}")
    public ResponseEntity<List<PostDTO>> getPendingPostReports(
            @PathVariable Integer projectId) {
        return ResponseEntity.ok(this.reportService.getPendingPostReports(projectId));
    }

    @GetMapping("/commentpending/{projectId}")
    public ResponseEntity<List<CommentDTO>> getPendingCommentReports(
            @PathVariable Integer projectId) {
        return ResponseEntity.ok( this.reportService.getPendingCommentReports(projectId));

    }
    @PutMapping("/resolve/{reportId}")
    public ResponseEntity<Void> resolveReport(
            @RequestHeader("Authorization") String token,
            @PathVariable int reportId) {
        reportService.resolveReport(reportId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dismiss/{reportId}")
    public ResponseEntity<Void> dismissReport(
            @RequestHeader("Authorization") String token,
            @PathVariable int reportId) {
        reportService.dismissReport(reportId);
        return ResponseEntity.ok().build();
    }
}
