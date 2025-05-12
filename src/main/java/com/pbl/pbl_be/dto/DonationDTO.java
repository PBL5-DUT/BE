package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Donation;

import java.time.LocalDateTime;

public class DonationDTO {
    private Integer donationId;
    private Integer projectId;
    private Integer userId;
    private Long amount;
    private String txnRef;
    private String goodDescription;
    private LocalDateTime createdAt;
    private String userName;

    public DonationDTO(Donation donation) {
        this.userId = donation.getUser().getUserId();
        this.donationId = donation.getDonationId();
        this.projectId = donation.getProjectId();
        this.userName = donation.getUser().getUsername();
        this.createdAt = donation.getCreatedAt();
        this.txnRef = donation.getTxnRef();
        this.goodDescription = donation.getGoodDescription();
        this.amount = donation.getAmount();
    }
}
