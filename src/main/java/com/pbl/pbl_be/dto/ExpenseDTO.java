package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Expense;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter

public class ExpenseDTO  {
    private Integer expenseId;
    private Integer projectId;
    private Integer senderId;
    private Integer receiverId;
    private String receiverName;
    private Long amount;
    private String purpose;
    private LocalDateTime createdAt;

}
