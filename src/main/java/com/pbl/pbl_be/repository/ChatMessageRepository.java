package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository  extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByProject_ProjectId(Integer projectId);
}
