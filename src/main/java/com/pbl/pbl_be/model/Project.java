package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    private String name;
    private String description;
    private String location;
    private String avatar_filepath;

    @ManyToOne
    @JoinColumn(name = "parent_project_id")
    private Project parent_project;

    @ManyToOne
    @JoinColumn(name = "pm_id", nullable = false)
    private User pm;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public enum Status {
        pending, approved, rejected, locked, finished, draft
    }
}
