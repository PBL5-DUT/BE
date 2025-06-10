package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ChatMessageDTO;
import com.pbl.pbl_be.mapper.ChatMessageMapper;
import com.pbl.pbl_be.model.ChatMessage;
import com.pbl.pbl_be.repository.ChatMessageRepository;
import com.pbl.pbl_be.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private ChatMessageRepository chatMessageRepository;


    @Override
    public List<ChatMessageDTO> getMessagesByProjectId(Integer projectId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findByProject_ProjectId(projectId);
        return chatMessages.stream()

                .map(chatmessage -> chatMessageMapper.toDto(chatmessage))
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO saveMessage(ChatMessageDTO messageDTO) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(messageDTO);
        chatMessage.setCreatedAt(java.time.LocalDateTime.now());

        ChatMessage mess = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toDto(mess);
    }
}

