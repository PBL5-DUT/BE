package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ProjectRequestDTO;
import com.pbl.pbl_be.dto.UserDTO;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ProjectRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ProjectRequestController {

    private final ProjectRequestService projectRequestService;
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public ProjectRequestController(ProjectRequestService projectRequestService, JwtTokenHelper jwtTokenHelper) {
        this.projectRequestService = projectRequestService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @PostMapping("/{projectId}/approve/{userId}")
    public void approveProjectRequest(@PathVariable int projectId, @PathVariable int userId) {
        projectRequestService.createProjectRequest(projectId, userId);
    }

    @PostMapping("/{projectId}/join")
    public void joinProject(@PathVariable Integer projectId, @RequestHeader("Authorization") String token) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        projectRequestService.createProjectRequest(projectId, userId);
    }

    @GetMapping("/{projectId}/check-join")
    public ProjectRequestDTO checkjoinProject(@PathVariable Integer projectId, @RequestHeader("Authorization") String token) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return projectRequestService.checkProjectRequest(projectId, userId);
    }
    @GetMapping("/{projectId}/approved")
    public List<UserDTO> getProjectMember(@PathVariable Integer projectId, @RequestHeader("Authorization") String token) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return projectRequestService.getProjectMember(projectId, userId);

    }
}