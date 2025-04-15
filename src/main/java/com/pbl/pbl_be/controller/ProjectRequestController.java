package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.service.ProjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class ProjectRequestController {
    @Autowired
    private ProjectRequestService projectRequestService;
    @Autowired
    private ProjectRequestRepository projectRequestRepository;

    @PostMapping("/{projectId}/approve/{userId}")
    public void approveProjectRequest(@PathVariable int projectId, @PathVariable int userId) {
        projectRequestService.createProjectRequest(projectId, userId);
    }


}
