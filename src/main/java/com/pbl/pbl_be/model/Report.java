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
    private Long report_id;

    @Enumerated(EnumType.STRING)
    private ReportType report_type;

    private Long report_item_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.pending;

    private LocalDateTime created_at;

    public enum ReportType {
        post, comment, user
    }

    public enum ReportStatus {
        pending, resolved
    }
}
