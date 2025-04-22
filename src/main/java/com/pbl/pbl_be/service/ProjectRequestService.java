package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.model.ProjectRequest;

public interface ProjectRequestService {
    int getApprovedParticipantsCount(int projectId);

    void createProjectRequest(int projectId,int userId);
    ProjectRequestDTO checkProjectRequest(int projectId, int userId);// New method
}