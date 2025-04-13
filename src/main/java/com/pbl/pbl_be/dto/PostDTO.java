package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private Integer postId;
    private Integer forumId;
    private Integer userId;
    private String content;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
