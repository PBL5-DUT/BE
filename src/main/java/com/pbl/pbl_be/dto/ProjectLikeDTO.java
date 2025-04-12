package com.pbl.pbl_be.dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ProjectLikeDTO {
    private Long like_id;
    private Long project_id;
    private Long user_id;
    private LocalDateTime created_at;
}

