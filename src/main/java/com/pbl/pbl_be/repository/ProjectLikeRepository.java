package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Integer> {
    @Query("SELECT COUNT(pl) FROM ProjectLike pl WHERE pl.project = :project AND pl.project.status = :status")
    int countLikesByApprovedProject(@Param("project") Project project, @Param("status") Project.Status status);
}