package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ChatMessageDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByProjectId(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer projectId) {
        return ResponseEntity.ok(chatMessageService.getMessagesByProjectId(projectId));
    }
}
