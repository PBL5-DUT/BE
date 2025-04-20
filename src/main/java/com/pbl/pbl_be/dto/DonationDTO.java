package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class DonationDTO {
    private Integer donationId;
    private Integer projectId;
    private Integer userId;
    private Long amount;
    private String status;
    private String txnRef;
    private LocalDateTime createdAt;
    private String userName;

    public DonationDTO(Integer donationId, Integer projectId ,Integer userId, Long amount, String status, String txnRef, LocalDateTime createdAt, String userName) {
        this.userId = userId;
        this.donationId = donationId;
        this.projectId = projectId;
        this.userName = userName;
        this.createdAt = createdAt;
        this.status = status;
        this.txnRef = txnRef;
        this.amount = amount;
    }
}
