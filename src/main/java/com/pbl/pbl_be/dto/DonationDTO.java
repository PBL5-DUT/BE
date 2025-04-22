package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Donation;

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

    public DonationDTO(Donation donation) {
        this.userId = donation.getUser().getUserId();
        this.donationId = donation.getDonationId();
        this.projectId = donation.getProjectId();
        this.userName = donation.getUser().getUsername();
        this.createdAt = donation.getCreatedAt();
        this.status = donation.getStatus().toString();
        this.txnRef = donation.getTxnRef();
        this.amount = donation.getAmount();
    }
}
