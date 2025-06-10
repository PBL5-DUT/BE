package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ForumDTO;
import com.pbl.pbl_be.mapper.ForumMapper;
import com.pbl.pbl_be.model.Forum;
import com.pbl.pbl_be.repository.ForumRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut; // Đã thêm
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"forums", "projectForums"}, allEntries = true) // Điều chỉnh
    public void createForum(ForumDTO forumDTO) {
        Forum forum = forumMapper.toEntity(forumDTO);
        forum.setCreatedAt(LocalDateTime.now()); // Đảm bảo thời gian tạo được thiết lập
        this.forumRepository.save(forum);
    }

    @Override
    @CachePut(value = "forums", key = "#forumDTO.forumId") // Điều chỉnh
    @CacheEvict(value = "projectForums", key = "#forumDTO.projectId", allEntries = false) // Điều chỉnh
    public void updateForum(ForumDTO forumDTO) {
        Forum forum = forumMapper.toEntity(forumDTO);
        if (forum != null && forum.getForumId() != null) {
            this.forumRepository.save(forum);
        } else {
            throw new IllegalArgumentException("Forum ID must not be null for update");
        }
    }

    @Override
    @CacheEvict(value = {"forums", "projectForums"}, allEntries = true) // Điều chỉnh
    public void deleteForum(int forumId) {
        Forum forumToDelete = this.forumRepository.findById(forumId)
                .orElseThrow(() -> new IllegalArgumentException("Forum not found with ID: " + forumId));
        this.forumRepository.delete(forumToDelete);
    }

    @Override
    @Cacheable(value = "forums", key = "#forumId")
    public ForumDTO getForumByForumId(int forumId) {
        Forum forum = this.forumRepository.findByForumId(forumId);
        if (forum == null) {
            return null;
        }
        return forumMapper.toDTO(forum);
    }

    @Override
    @Cacheable(value = "projectForums", key = "#projectId")
    public List<ForumDTO> getForumsByProjectId(int projectId) {
        List<Forum> forums = this.forumRepository.findByProject_ProjectId(projectId);
        return forums.stream().map(ForumDTO::new).toList();
    }
}