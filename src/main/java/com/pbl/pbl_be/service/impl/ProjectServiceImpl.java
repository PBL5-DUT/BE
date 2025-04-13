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

    public List<ProjectDTO> getApprovedProjectsSorted(String sort, String direction) {
        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        List<Project> projects = this.projectRepo.findAllByStatus(Project.Status.approved, sortBy);
        return projects.stream().map(projectMapper::toDTO).collect(Collectors.toList());
    }
}
