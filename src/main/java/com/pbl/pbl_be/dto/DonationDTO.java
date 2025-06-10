package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Donation;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class DonationDTO  {
    private Integer donationId;
    private Integer projectId;
    private Integer userId;
    private Long amount;
    private String txnRef;
    private Donation.Type type;
    private String goodDescription;
    private LocalDateTime createdAt;
    private String userName;
}
