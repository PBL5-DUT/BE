package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.model.Report;

import java.util.List;

public interface ReportService {
    void addReport(ReportDTO reportDTO,  int userId);
    void resolveReport(int reportId);
    void dismissReport(int reportId);
    List<ReportDTO> getPendingReports(Report.ReportType type);

}
