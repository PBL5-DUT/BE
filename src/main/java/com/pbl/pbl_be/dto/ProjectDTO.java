package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectDTO {
    private Integer projectId;
    private String name;
    private String description;
    private String location;
    private String avatarFilepath;
    private Integer parentProjectId;
    private Integer pmId;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer maxParticipants;
    private String status;
    private String bank;
    private Integer participantsCount;
    private Integer likesCount;
    private Boolean isLiked;
    private Boolean hasJoined;
}