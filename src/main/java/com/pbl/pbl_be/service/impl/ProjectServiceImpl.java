package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.config.AppConstants;
import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.exception.ResourceNotFoundException;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.service.ProjectService;

import com.pbl.pbl_be.repository.ProjectRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ModelMapper mm;

    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream().map(this::projectToDto).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectById(Integer projectId) {
         Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        return this.projectToDto(project);
    }

    public ProjectDTO createProject(ProjectDTO projectDto) {
        Project project = this.mm.map(projectDto, Project.class);
        project.setStartTime(LocalDate.from(LocalDateTime.now()));
        project.setStatus(Project.Status.pending);
        Project savedProject = this.projectRepo.save(project);
        return this.projectToDto(savedProject);
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
        Project updatedProject = this.projectRepo.save(project);
        return this.projectToDto(updatedProject);
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
        return projects.stream().map(this::projectToDto).collect(Collectors.toList());
    }


    private ProjectDTO projectToDto(Project project) {
        return this.mm.map(project, ProjectDTO.class);
    }
}

