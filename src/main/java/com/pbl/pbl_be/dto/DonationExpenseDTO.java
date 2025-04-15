package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class DonationExpenseDTO {
    private Integer expenseId;
    private Integer projectId;
    private Integer senderId;
    private Integer receiverId;
    private Long amount;
    private String purpose;
    private LocalDateTime createdAt;
}
