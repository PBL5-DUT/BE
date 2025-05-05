package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Integer projectId);
    List<ProjectDTO> getProjectsByPmId(Integer pmId);
    List<ProjectDTO> getProjectsByStatus(String status);
    ProjectDTO createProject(ProjectDTO projectDto);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project);
    void deleteProject(Integer projectId);



    List<ProjectDTO> getProjectsByStatusRemaining(int userId);
    List<ProjectDTO> getProjectsByStatusSorted(String sort,int userId);
    List<ProjectDTO> getProjectsLiked(int userId);

}