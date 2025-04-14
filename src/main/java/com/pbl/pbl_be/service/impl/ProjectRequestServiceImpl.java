package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectRequestServiceImpl implements ProjectRequestService {

    @Autowired
    private ProjectRequestRepository projectRequestRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;// Added to fetch Project by ID

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
        projectRequest.setUser(user); // Ensure userId exists in ProjectRequest
        projectRequest.setStatus(ProjectRequest.Status.pending); // Ensure PENDING exists in Status enum
        projectRequestRepository.save(projectRequest);
    }
}