package com.pbl.pbl_be.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DonationStatsDTO {
    private Integer projectId;
    private LocalDateTime createdAt;
    private Long amount;

    public DonationStatsDTO(Integer projectId, LocalDateTime createdAt, Long amount) {
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.amount = amount;
    }
}