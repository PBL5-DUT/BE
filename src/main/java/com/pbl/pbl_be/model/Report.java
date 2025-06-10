package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter @Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(name = "report_item_id")
    private Long reportItemId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.pending;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum ReportType {
        post, comment, user
    }
    public enum ReportStatus {
        pending, resolved
    }
}
