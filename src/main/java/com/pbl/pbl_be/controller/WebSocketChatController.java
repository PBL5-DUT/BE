package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ChatMessageDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class WebSocketChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/chat.send") // /app/chat.send
    public void sendMessage(@Payload ChatMessageDTO messageDTO) {
        // Lưu tin nhắn vào DB
        ChatMessageDTO savedMessage = chatMessageService.saveMessage(messageDTO);

        // Gửi tin nhắn tới những client đang sub vào /topic/chat/project/{projectId}
        messagingTemplate.convertAndSend(
                "/topic/chat/project/" + messageDTO.getProjectId(),
                savedMessage
        );
        System.out.println("Message sent to project " + messageDTO.getProjectId() + ": " + savedMessage.getMessage());
    }
}

