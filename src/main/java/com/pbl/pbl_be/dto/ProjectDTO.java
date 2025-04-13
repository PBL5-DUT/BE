package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectDTO {
    private Integer projectId;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String location;
    @Getter @Setter
    private String avatarFilepath;
    private Integer parentProjectId;
    private Integer pmId;
    @Getter @Setter
    private LocalDate startTime;
    @Getter @Setter
    private LocalDate endTime;
    @Getter @Setter
    private Integer maxParticipants;
    @Getter @Setter
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
