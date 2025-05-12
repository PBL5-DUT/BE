package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ForumDTO;
import com.pbl.pbl_be.model.Forum;
import com.pbl.pbl_be.repository.ForumRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ForumMapper {

    @Autowired
    private ProjectRepository projectRepository;


    public Forum toEntity(ForumDTO dto) {
        Forum forum = new Forum();
        forum.setForumId(dto.getForumId());
        forum.setTitle(dto.getTitle());
        forum.setProject(projectRepository.findByProjectId(dto.getProjectId()));
        forum.setCreatedAt(LocalDateTime.now());
        return forum;
    }

    public ForumDTO toDTO(Forum forum) {
        ForumDTO dto = new ForumDTO();
        dto.setForumId(forum.getForumId());
        dto.setTitle(forum.getTitle());
        dto.setProjectId(forum.getProject().getProjectId());
        return dto;
    }
}
