package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ForumDTO;
import com.pbl.pbl_be.mapper.ForumMapper;
import com.pbl.pbl_be.model.Forum;
import com.pbl.pbl_be.repository.ForumRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ForumMapper forumMapper;


    @Override
    public void createForum(ForumDTO forumDTO) {
        Forum forum = forumMapper.toEntity(forumDTO);
        this.forumRepository.save(forum);
    }

    @Override
    public void updateForum(ForumDTO forumDTO) {
        Forum forum=forumMapper.toEntity(forumDTO);
        this.forumRepository.save(forum);
    }

    @Override
    public void deleteForum(int forumId) {
        this.forumRepository.delete(this.forumRepository.findById(forumId).get());
    }

    @Override
    public ForumDTO getForumByForumId(int forumId) {
        Forum forum= this.forumRepository.findByForumId(forumId);
        if (forum == null) {
            return null;
        }
        return forumMapper.toDTO(forum);

    }

    @Override
    public List<ForumDTO> getForumsByProjectId(int projectId) {
        List<Forum> forums = this.forumRepository.findByProject_ProjectId(projectId);
        return forums.stream().map(ForumDTO::new).toList();
    }
}
