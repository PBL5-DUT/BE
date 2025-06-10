package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectLike;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectLikeRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectLikeService;
import jakarta.transaction.Transactional; // Đã thêm
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ProjectLikeServiceImpl implements ProjectLikeService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectLikeRepository projectLikeRepository;

    public ProjectLikeServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ProjectLikeRepository projectLikeRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectLikeRepository = projectLikeRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "projectLikes", allEntries = true) // Xóa toàn bộ cache liên quan đến project likes
    public void createProjectLike(Integer projectId, Integer userId) {
        if (projectLikeRepository.existsByProject_ProjectIdAndUser_Id(projectId, userId)) {
            return;
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectLike like = new ProjectLike();
        like.setProject(project);
        like.setUser(user);

        projectLikeRepository.save(like);
    }


    @Override
    @Transactional
    @CacheEvict(value = "projectLikes", allEntries = true) // Xóa toàn bộ cache liên quan đến project likes
    public void deleteProjectLike(Integer projectId, Integer userId) {
        if (projectLikeRepository.existsByProject_ProjectIdAndUser_Id(projectId, userId)) {
            projectLikeRepository.deleteByProject_ProjectIdAndUser_Id(projectId, userId);
        }
    }
}
