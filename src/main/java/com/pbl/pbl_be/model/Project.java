package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "avatar_filepath")
    private String avatarFilepath;

    @Column(name = "bank")
    private String bank;

    @ManyToOne
    @JoinColumn(name = "parent_project_id")
    private Project parentProject;

    @ManyToOne
    @JoinColumn(name = "pm_id", nullable = false)
    private User pm;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "max_participants")
    private Integer maxParticipants;
    @Enumerated(EnumType.STRING)

    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Status {
        pending, approved, rejected, locked, finished, draft

    }
}
