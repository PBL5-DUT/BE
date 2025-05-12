package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ForumDTO;

import java.util.List;

public interface ForumService {
    void createForum(ForumDTO forumDTO);
    void updateForum(ForumDTO forumDTO);
    void deleteForum(int forumId);
    ForumDTO getForumByForumId(int forumId);
    List<ForumDTO> getForumsByProjectId(int projectId);
}
