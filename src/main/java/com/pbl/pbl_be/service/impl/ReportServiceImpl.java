package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.model.Report;
import com.pbl.pbl_be.repository.ReportRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict; // Added this import
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    @CacheEvict(value = "reports", allEntries = true) // Invalidate all report caches when a new report is added
    public void addReport(ReportDTO reportDTO, int userId) {
        Report report = new Report();
        report.setReportItemId(Long.valueOf(reportDTO.getReportItemId()));
        report.setReportType(Report.ReportType.valueOf(reportDTO.getReportType()));
        report.setReason(reportDTO.getReason());
        report.setCreatedAt(java.time.LocalDateTime.now());
        report.setStatus(Report.ReportStatus.pending);
        report.setUser(userRepository.findByUserId(userId));
        if (report.getUser() == null) {
            throw new RuntimeException("User not found");
        }
        this.reportRepository.save(report);
    }


    public List<ReportDTO> getPendingReports(Report.ReportType type) {
        List<Report> reports;

        if (type != null ) {
            reports = reportRepository.findByReportTypeAndStatusOrderByCreatedAtDesc(type, Report.ReportStatus.valueOf("PENDING"));
        }

        return List.of();
    }

    public void resolveReport(int reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));


    }

    public void dismissReport(int reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        reportRepository.save(report);
    }

}


