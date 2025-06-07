package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectDTO;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;

import com.pbl.pbl_be.dto.UserDTO;


import java.util.List;

public interface ProjectService {


    List<ProjectDTO> getAllProjects();
    List<ProjectDTO> getProjectsByPmId(Integer pmId);
    ProjectDTO createProject(ProjectDTO projectDto);
    ProjectDTO updateProject(Integer projectId, ProjectDTO project);

    ProjectDTO getProjectById(Integer projectId, Integer userId);
    void deleteProject(Integer projectId);

    List<ProjectDTO> getProjectsByStatusRemaining(Integer userId);
    List<ProjectDTO> getProjectsByStatusSorted(String sort, Integer userId);
    List<ProjectDTO> getProjectsLiked(Integer userId);

    List<ProjectDTO> getJoinedProjects(Integer userId);

    ProjectDTO lockProject(Integer projectId, ProjectDTO projectDto);
    ProjectDTO copyProject(Project projectId, ProjectDTO projectDto);


    List<ProjectDTO> getChildProjectsByParentId(Integer parentProjectId, Integer userId);

    void likeProject(Integer projectId, Integer userId);
}