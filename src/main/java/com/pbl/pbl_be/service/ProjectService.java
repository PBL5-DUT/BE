package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;


import java.util.List;
import java.util.Optional;


public interface ProjectService {

    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Integer projectId);
    List<ProjectDTO> getProjectsByPmId(Integer userId);// dự án của user làm pm
    List<ProjectDTO> getProjectsByStatus(String status); // dự án của user làm pm
    ProjectDTO createProject(ProjectDTO projectDto);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project);
    void deleteProject(Integer projectId);

    List<ProjectDTO> getProjectsByStatusRemaining();


    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }


