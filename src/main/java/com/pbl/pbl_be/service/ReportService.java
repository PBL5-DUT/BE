package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ReportDTO;

public interface ReportService {
    void addReport(ReportDTO reportDTO,  int userId);
}
