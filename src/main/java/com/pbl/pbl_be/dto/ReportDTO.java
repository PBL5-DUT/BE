package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private Integer reportId;
    private String reportType;
    private Integer reportItemId;
    private Integer userId;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
