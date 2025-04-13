package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByStatus(Project.Status status);
    List<Project> findAllByStatus(Project.Status status, Sort sort);
    Project findByProjectId(Integer projectId);
}
