package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.dto.ProjectDTO;

import com.pbl.pbl_be.model.Project;

import com.pbl.pbl_be.dto.UserDTO;

import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepository userRepository;


    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(
            @PathVariable Integer projectId,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId= jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(this.projectService.getProjectById(projectId, userId));
    }

    @GetMapping("/pm/{pmId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByPmId(@PathVariable Integer pmId){
        return ResponseEntity.ok(this.projectService.getProjectsByPmId(pmId));
    }



    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(
            @RequestBody @Valid ProjectDTO projectDto,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        ProjectDTO createdProject = this.projectService.createProject(projectDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Integer projectId,
            @Valid @RequestBody ProjectDTO project,
            @RequestHeader("Authorization") String token
    ) {
        ProjectDTO updatedProject = this.projectService.updateProject(projectId, project);
        return ResponseEntity.ok(updatedProject);
    }

    @PutMapping("/{projectId}/lock")
    public ResponseEntity<ProjectDTO> lockProject(
            @PathVariable Integer projectId,
            @RequestBody ProjectDTO projectDto,
            @RequestHeader("Authorization") String token
    ) {
        ProjectDTO lockedProject = projectService.lockProject(projectId, projectDto);
        return ResponseEntity.ok(lockedProject);
    }

    @PostMapping("/{projectId}/copy")
    public ResponseEntity<ProjectDTO> copyProject(
            @PathVariable Project projectId,
            @RequestBody @Valid ProjectDTO projectDto,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        ProjectDTO copyProject = this.projectService.copyProject(projectId, projectDto);
        return new ResponseEntity<>(copyProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        this.projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ProjectDTO>> getProjectsByStatusSorted(
            @RequestParam(defaultValue = "startTime") String sort,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        if (sort.equals("remaining")) {
            return ResponseEntity.ok(projectService.getProjectsByStatusRemaining(userId));
        }
        if (sort.equals("liked")) {
            return ResponseEntity.ok(projectService.getProjectsLiked(userId));
        }
        return ResponseEntity.ok(projectService.getProjectsByStatusSorted(sort, userId));
    }
    @GetMapping("/joined")
    public ResponseEntity<List<ProjectDTO>> getJoinedProjects(
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(projectService.getJoinedProjects(userId));
    }
    @GetMapping("/child-projects/{parentProjectId}")
    public ResponseEntity<List<ProjectDTO>> getChildProjectsByParentId(
            @PathVariable Integer parentProjectId,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(projectService.getChildProjectsByParentId(parentProjectId, userId));
    }

    @PostMapping("/{projectId}/like")
    public ResponseEntity<Void> likeProject(
            @PathVariable Integer projectId,
            @RequestHeader("Authorization") String token
    ) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        projectService.likeProject(projectId, userId);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/userprofile/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserId(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token
    ) {
        Integer currentUserId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        List<ProjectDTO> projects = projectService.getProjectsByUserId(userId, currentUserId);
        return ResponseEntity.ok(projects);
    }
}