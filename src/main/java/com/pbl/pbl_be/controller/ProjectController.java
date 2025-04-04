package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Lấy tất cả các dự án
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Thêm mới dự án
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        // Lưu dự án vào cơ sở dữ liệu
        Project savedProject = projectRepository.save(project);

        // Trả về thông tin dự án đã lưu cùng với mã trạng thái CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }
}

