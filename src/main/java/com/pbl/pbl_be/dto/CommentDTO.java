package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer commentId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}