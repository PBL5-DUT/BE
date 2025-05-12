package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Expense;

import java.time.LocalDateTime;

public class ExpenseDTO {
    private Integer expenseId;
    private Integer projectId;
    private Integer senderId;
    private Integer receiverId;
    private Long amount;
    private String purpose;
    private LocalDateTime createdAt;

    public ExpenseDTO(Expense expense) {
        this.expenseId = expense.getExpenseId();
        this.projectId = expense.getProject().getProjectId();
        this.senderId = expense.getSender().getUserId();
        this.receiverId = expense.getReceiver() != null ? expense.getReceiver().getUserId() : null;
        this.amount = expense.getAmount();
        this.purpose = expense.getPurpose();
        this.createdAt = expense.getCreatedAt();
    }
}
