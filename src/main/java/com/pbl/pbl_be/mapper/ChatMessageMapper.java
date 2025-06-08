package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ChatMessageDTO;
import com.pbl.pbl_be.model.ChatMessage;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatMessage toEntity(ChatMessageDTO chatMessageDTO,int userId) {
        if (chatMessageDTO == null) {
            return null;
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageId(chatMessageDTO.getMessageId());
        chatMessage.setMessage(chatMessageDTO.getMessage());
        chatMessage.setUser(userRepository.findByUserId(userId));
        chatMessage.setProject(projectRepository.findByProjectId(chatMessageDTO.getProjectId()));
        chatMessage.setCreatedAt(chatMessageDTO.getCreatedAt());
        return chatMessage;
    }
    public ChatMessageDTO toDto(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setMessageId(chatMessage.getMessageId());
        chatMessageDTO.setMessage(chatMessage.getMessage());
        chatMessageDTO.setProjectId(chatMessage.getProject().getProjectId());
        chatMessageDTO.setCreatedAt(chatMessage.getCreatedAt());
        chatMessageDTO.setUserId(chatMessage.getUser() != null ? chatMessage.getUser().getUserId(): null);
        chatMessageDTO.setUserName(chatMessage.getUser() != null ? chatMessage.getUser().getUsername() : null);
        chatMessageDTO.setAvatarFilePath(chatMessage.getUser() != null ? chatMessage.getUser().getAvatarFilepath() : null);
        return chatMessageDTO;
    }


}
