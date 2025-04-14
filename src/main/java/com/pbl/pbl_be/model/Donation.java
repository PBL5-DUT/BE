package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter @Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Integer donationId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column
    private String txn_ref;

    @Enumerated(EnumType.STRING)
    private Donation.Status status = Donation.Status.pending;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;



    public enum Status {
        pending, approved, failed
    }
}
