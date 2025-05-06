package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findProjectsByStatus(Project.Status status);
    List<Project> findAllByStatus(Project.Status status, Sort sort);
    Project findByProjectId(Integer projectId);
    List<Project> findProjectsByPm(Project project);
    List<Project> findProjectsByPmId(Integer pmId);
}
