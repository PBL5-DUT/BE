package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Integer projectId);
    List<ProjectDTO> getApprovedProjectsSorted(String sort, String direction);
    ProjectDTO createProject(ProjectDTO project);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project);
    void deleteProject(Integer projectId);
}
