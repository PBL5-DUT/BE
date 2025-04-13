package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ForumDTO {
    private Integer forumId;
    private Integer projectId;
    private String title;
    private LocalDateTime createdAt;

}
