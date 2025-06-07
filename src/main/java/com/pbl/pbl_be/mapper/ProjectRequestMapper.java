package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRequestMapper {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectRequest toEntity(ProjectRequestDTO dto) {
        ProjectRequest projectRequest = new ProjectRequest();

        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + dto.getProjectId()));
            projectRequest.setProject(project);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getUserId()));
            projectRequest.setUser(user);
        }

        projectRequest.setStatus(ProjectRequest.Status.valueOf(dto.getStatus()));
        projectRequest.setCreatedAt(dto.getCreatedAt());

        return projectRequest;
    }

    public ProjectRequestDTO toDTO(ProjectRequest projectRequest) {
        ProjectRequestDTO dto = new ProjectRequestDTO();

        dto.setProjectId(projectRequest.getProject().getProjectId());
        dto.setUserId(projectRequest.getUser().getUserId());
        dto.setStatus(projectRequest.getStatus().name());
        dto.setCreatedAt(projectRequest.getCreatedAt());

        dto.setUserName(projectRequest.getUser().getUsername());
        dto.setUserAvatar(projectRequest.getUser().getAvatarFilepath());

        return dto;
    }
}