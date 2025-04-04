package com.pbl.pbl_be.service;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }
}
