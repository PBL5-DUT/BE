package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String content;
    private Integer parentCommentId;
    private LocalDateTime createdAt;
}
