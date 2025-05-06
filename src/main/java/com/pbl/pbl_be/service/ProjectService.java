package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllProjects(Integer userId);
    ProjectDTO getProjectById(Integer projectId, Integer userId);
    List<ProjectDTO> getProjectsByPmId(Integer userId); // dự án của user làm pm
    List<ProjectDTO> getProjectsByStatus(String status, Integer userId); // dự án của user làm pm
    ProjectDTO createProject(ProjectDTO projectDto, Integer userId);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project, Integer userId);
    void deleteProject(Integer projectId);

    List<ProjectDTO> getProjectsByStatusRemaining(Integer userId);
    List<ProjectDTO> getProjectsByStatusSorted(String sort, Integer userId);
    List<ProjectDTO> getProjectsLiked(Integer userId);

    List<ProjectDTO> getJoinedProjects(Integer userId);
}