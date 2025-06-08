package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.mapper.ProjectRequestMapper;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    @Cacheable(value = "projectRequests", key = "#projectId")
    public List<UserDTO> getProjectMember(Integer projectId, Integer userId) {
        List<User> users = projectRequestRepository.findUsersByProjectIdAndStatus(projectId, ProjectRequest.Status.approved);
        return users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(user.getUserId());
                    userDTO.setUsername(user.getUsername());
                    userDTO.setFullName(user.getFullName());
                    userDTO.setAvatarFilepath(user.getAvatarFilepath());
                    return userDTO;
                })
                .toList();
    }

    @Override
    public List<UserDTO> getPendingProjectMembers(Integer projectId, Integer userId) {
        List<User> users = projectRequestRepository.findUsersByProjectIdAndStatus(projectId, ProjectRequest.Status.pending);
        return users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(user.getUserId());
                    userDTO.setUsername(user.getUsername());
                    userDTO.setFullName(user.getFullName());
                    userDTO.setAvatarFilepath(user.getAvatarFilepath());
                    return userDTO;
                })
                .toList();
    }

    @Override
    public void acceptProjectRequest(int projectId, int userId) {
        ProjectRequest projectRequest = projectRequestRepository.findByProject_ProjectIdAndUser_Id(projectId, userId);
        if (projectRequest == null) {
            throw new IllegalArgumentException("Project request not found for project ID: " + projectId + " and user ID: " + userId);
        }
        if (projectRequest.getStatus() == ProjectRequest.Status.approved) {
            throw new IllegalStateException("Project request is already approved");
        }
        if (projectRequest.getStatus() == ProjectRequest.Status.rejected) {
            throw new IllegalStateException("Cannot approve a rejected project request");
        }
        projectRequest.setStatus(ProjectRequest.Status.approved);
        projectRequestRepository.save(projectRequest);
    }
}