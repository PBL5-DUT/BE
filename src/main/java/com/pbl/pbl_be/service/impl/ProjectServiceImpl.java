package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.exception.ResourceNotFoundException;
import com.pbl.pbl_be.mapper.ProjectMapper;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepo.findAll();
        for (Project project : projects) {

        }
        return projects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectById(Integer projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        return projectMapper.toDTO(project);
    }

    @Override
    public List<ProjectDTO> getProjectsByPmId(Integer userId) {
        User user= this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Project> projects = projectRepo.findProjectsByPm(user);
        return projects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getProjectsByStatus(String status)
    {
        Project.Status projectStatus = Project.Status.valueOf(status);
        List<Project> projects = projectRepo.findProjectsByStatus(projectStatus);
        return projects.stream()
                .map(projectMapper::toDTO)
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
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(Integer projectId) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        this.projectRepo.delete(project);
    }

    @Override
    public  List<ProjectDTO> getProjectsByStatusSorted(String sort, String direction) {
        // Fetch all approved projects
        List<Project> approvedProjects = this.projectRepo.findProjectsByStatus(Project.Status.approved);

        // Convert to DTOs
        List<ProjectDTO> projectDTOs = approvedProjects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());

        // Sort the DTOs based on the specified attribute and direction
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

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }
        projectDTOs.sort(comparator);

        return projectDTOs;
    }
    @Override
    public List<ProjectDTO> getProjectsByStatusRemaining() {
        List<Project> approvedProjects = this.projectRepo.findProjectsByStatus(Project.Status.approved);

        LocalDateTime now = LocalDateTime.now();
        List<ProjectDTO> projectDTOs = approvedProjects.stream()
                .filter(project -> project.getEndTime().isAfter(ChronoLocalDate.from(now))) // Filter condition
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());

        projectDTOs.sort(Comparator.comparing(ProjectDTO::getEndTime));

        return projectDTOs;
    }
}