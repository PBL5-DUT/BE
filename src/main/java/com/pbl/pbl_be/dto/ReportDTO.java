package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDTO {
    private Integer reportId;
    private String reportType;
    private Integer reportItemId;
    private Integer userId;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
