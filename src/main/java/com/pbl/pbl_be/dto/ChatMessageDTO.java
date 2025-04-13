package com.pbl.pbl_be.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private Integer messageId;
    private Integer projectId;
    private Integer userId;
    private String message;
    private LocalDateTime createdAt;
}
