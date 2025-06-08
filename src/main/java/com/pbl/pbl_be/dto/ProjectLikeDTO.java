package com.pbl.pbl_be.dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ProjectLikeDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer likeId;
    private Integer projectId;
    private Integer userId;
    private LocalDateTime createdAt;
}

