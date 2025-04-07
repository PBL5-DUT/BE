package com.pbl.pbl_be.model.PM;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
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
    private String parent_project_id;
    private String pm_id;
    private LocalDate start_time;
    private LocalDate end_time;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public void setId(Long id) {
        this.project_id = id;
    }
}

