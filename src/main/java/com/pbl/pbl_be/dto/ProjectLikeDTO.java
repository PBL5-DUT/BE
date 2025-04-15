package com.pbl.pbl_be.dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ProjectLikeDTO {
    private Integer likeId;
    private Integer projectId;
    private Integer userId;
    private LocalDateTime createdAt;
}

