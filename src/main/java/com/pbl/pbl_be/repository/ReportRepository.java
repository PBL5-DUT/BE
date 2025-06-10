package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByReportTypeAndStatusOrderByCreatedAtDesc(Report.ReportType reportType, Report.ReportStatus status);
}
