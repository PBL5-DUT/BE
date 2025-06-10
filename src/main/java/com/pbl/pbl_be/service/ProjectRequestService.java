package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.model.ProjectRequest;

import java.util.List;

public interface ProjectRequestService {
    int getApprovedParticipantsCount(int projectId);

    void createProjectRequest(int projectId,int userId);
    ProjectRequestDTO checkProjectRequest(int projectId, int userId);// New method

    List<UserDTO> getProjectMember(Integer projectId, Integer userId);
    List<UserDTO> getPendingProjectMembers(Integer projectId, Integer userId);
    void acceptProjectRequest(int projectId, int userId);

}