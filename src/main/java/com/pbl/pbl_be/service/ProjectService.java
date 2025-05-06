package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {


    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Integer projectId);
    List<ProjectDTO> getProjectsByPmId(Integer pmId);
    ProjectDTO createProject(ProjectDTO projectDto);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project);

    List<ProjectDTO> getAllProjects(Integer userId);
    ProjectDTO getProjectById(Integer projectId, Integer userId);
    List<ProjectDTO> getProjectsByStatus(String status, Integer userId); // dự án của user làm pm

    void deleteProject(Integer projectId);

    List<ProjectDTO> getProjectsByStatusRemaining(Integer userId);
    List<ProjectDTO> getProjectsByStatusSorted(String sort, Integer userId);
    List<ProjectDTO> getProjectsLiked(Integer userId);

    List<ProjectDTO> getJoinedProjects(Integer userId);
}