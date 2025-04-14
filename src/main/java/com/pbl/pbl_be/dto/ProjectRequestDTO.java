package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ProjectRequestDTO {
    private Integer requestId;
    private Integer projectId;
    private Integer userId;
    private String status;
    private LocalDateTime createdAt;

}
