package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(String status);
}
