package com.pbl.pbl_be.controller;


import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ProjectService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @GetMapping("/")
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Integer projectId){
        return ResponseEntity.ok(this.projectService.getProjectById(projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid ProjectDTO projectDto) {
        ProjectDTO createdProject = this.projectService.createProject(projectDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer projectId, @Valid @RequestBody ProjectDTO project) {
        ProjectDTO updatedProject = this.projectService.updateProject(projectId, project);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId) {
        this.projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/approved")
    public List<ProjectDTO> getProjectsByStatusSorted(
            @RequestParam(defaultValue = "startTime") String sort,
            @RequestHeader("Authorization") String token
    ) {

        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        if(sort.equals("remaining")) {
            return projectService.getProjectsByStatusRemaining(userId);
        }
        if(sort.equals("liked"))
        {
            return projectService.getProjectsLiked(userId);
        }
        return projectService.getProjectsByStatusSorted(sort,userId);
    }

}
