package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Integer> {

    @Query("SELECT COUNT(pr.project.projectId) FROM ProjectRequest pr WHERE pr.status = 'approved' AND pr.project.projectId = :projectId")
    int countApprovedPmIdsByProjectId(@Param("projectId") Integer projectId);

    ProjectRequest findByProject_ProjectIdAndUser_Id(int projectId, int userId);

    Integer countApprovedParticipantsByProject(Project project);
}