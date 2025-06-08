package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer commentId;
    private Integer userId;
    private String userName;
    private String avatarFilePath;
    private Integer postId;
    private String content;
    private Integer parentCommentId;
    private LocalDateTime createdAt;
}