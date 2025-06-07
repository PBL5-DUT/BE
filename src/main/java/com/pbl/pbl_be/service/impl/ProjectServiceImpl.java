package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.exception.ResourceNotFoundException;
import com.pbl.pbl_be.mapper.ProjectMapper;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectLike;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectLikeRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectLikeRepository projectLikeRepo;

    @Autowired
    private ProjectRequestRepository projectRequestRepo;

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream()
                .map(project -> projectMapper.toDTO(project))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectById(Integer projectId, Integer userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        return projectMapper.toDTO(project, userId);
    }

    @Override
    public List<ProjectDTO> getProjectsByPmId(Integer pmId) {
        List<Project> projects = projectRepo.findProjectsByPmId(pmId);
        return projects.stream()
                .map(project -> projectMapper.toDTO(project))
                .collect(Collectors.toList());
    }



    @Override
    public ProjectDTO createProject(ProjectDTO projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setStatus(Project.Status.pending);
        Project savedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @Override
    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDto) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStartTime(projectDto.getStartTime());
        project.setEndTime(projectDto.getEndTime());
        project.setStatus(Project.Status.valueOf(projectDto.getStatus()));
        project.setMaxParticipants(projectDto.getMaxParticipants());
        project.setBank(projectDto.getBank());
        project.setAvatarFilepath(projectDto.getAvatarFilepath());
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public List<ProjectDTO> getProjectsByStatusSorted(String sort, Integer userId) {
        List<Project> approvedProjects = this.projectRepo.findProjectsByStatus(Project.Status.approved);
        List<ProjectDTO> projectDTOs = approvedProjects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());

        Comparator<ProjectDTO> comparator;
        switch (sort) {
            case "startTime":
                comparator = Comparator.comparing(ProjectDTO::getStartTime);
                break;
            case "likesCount":
                comparator = Comparator.comparing(ProjectDTO::getLikesCount);
                break;
            case "participantsCount":
                comparator = Comparator.comparing(ProjectDTO::getParticipantsCount);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field!");
        }

        projectDTOs.removeIf(projectDTO ->
                projectDTO.getPmId() != null && projectDTO.getPmId().equals(userId)
        );

        projectDTOs.sort(comparator.reversed());
        return projectDTOs;
    }

    @Override
    public List<ProjectDTO> getProjectsByStatusRemaining(Integer userId) {
        List<Project> approvedProjects = this.projectRepo.findProjectsByStatus(Project.Status.approved);

        LocalDateTime now = LocalDateTime.now();
        List<ProjectDTO> projectDTOs = approvedProjects.stream()
                .filter(project -> project.getEndTime().isAfter(now.toLocalDate()))
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());

        projectDTOs.removeIf(projectDTO ->
                projectDTO.getPmId() != null && projectDTO.getPmId().equals(userId)
        );

        projectDTOs.sort(Comparator.comparing(ProjectDTO::getEndTime));
        return projectDTOs;
    }

    @Override
    public List<ProjectDTO> getProjectsLiked(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<ProjectLike> projectLikes = this.projectLikeRepo.findProjectLikesByUser(user);
        List<Project> returnProjects = new ArrayList<>();

        for (ProjectLike projectLike : projectLikes) {
            Project project = projectLike.getProject();
            if (project.getPm().getUserId().equals(userId)) {
                continue;
            }
            returnProjects.add(project);
        }

        return returnProjects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getJoinedProjects(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List <Project> projects=this.projectRequestRepo.findByUser_IdAndStatus(userId, ProjectRequest.Status.approved)
                .stream()
                .map(projectRequest -> projectRequest.getProject())
                .collect(Collectors.toList());
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());

        return projectDTOs;
    }

    @Override
    public void deleteProject(Integer projectId) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        this.projectRepo.delete(project);
    }

    @Override
    public ProjectDTO lockProject(Integer projectId, ProjectDTO projectDto) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));

        project.setStatus(Project.Status.valueOf(projectDto.getStatus()));
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public ProjectDTO copyProject(Project projectId, ProjectDTO projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setParentProject(projectId);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setStatus(Project.Status.draft);
        Project savedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(savedProject);
    }
}
