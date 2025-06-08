package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {
    void sendMessage(ChatMessageDTO chatMessageDTO, int userId);

    List<ChatMessageDTO> getMessagesByProjectId(Integer projectId);
}
