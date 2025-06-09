package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    private Integer messageId;
    private Integer projectId;
    private Integer userId;
    private String userName;
    private String avatarFilePath;
    private String message;
    private LocalDateTime createdAt;
}
