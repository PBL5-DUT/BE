package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.mapper.ProjectRequestMapper;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectRequestServiceImpl implements ProjectRequestService {

    @Autowired
    private ProjectRequestRepository projectRequestRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProjectRequestMapper projectRequestMapper;

    @Override
    public int getApprovedParticipantsCount(int projectId) {
        return projectRequestRepository.countApprovedPmIdsByProjectId(projectId);
    }

    @Override
    public void createProjectRequest(int projectId, int userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProject(project); // Set the project
        projectRequest.setUser(user);
        projectRequest.setCreatedAt(LocalDateTime.now());// Ensure userId exists in ProjectRequest
        projectRequest.setStatus(ProjectRequest.Status.pending);
        projectRequestRepository.save(projectRequest);
    }

    @Override
    public ProjectRequestDTO checkProjectRequest(int projectId, int userId) {
        ProjectRequest projectRequest = projectRequestRepository.findByProject_ProjectIdAndUser_Id(projectId, userId);
        if (projectRequest != null) {
            return projectRequestMapper.toDTO(projectRequest);
        } else {
            return null;
        }
    }
}