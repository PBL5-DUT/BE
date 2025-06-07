package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donation_expenses")
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    @Column(name = "project_id")
    private Integer projectId;


    @Column(name = "sender_id", nullable = false)
    private Integer senderId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private Long amount;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
