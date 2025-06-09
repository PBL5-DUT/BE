package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {

    List<ChatMessageDTO> getMessagesByProjectId(Integer projectId);

    ChatMessageDTO saveMessage(ChatMessageDTO messageDTO);
}
