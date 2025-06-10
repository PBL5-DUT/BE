package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Integer> {

    @Query("SELECT COUNT(pr.project.projectId) FROM ProjectRequest pr WHERE pr.status = 'approved' AND pr.project.projectId = :projectId")
    int countApprovedPmIdsByProjectId(@Param("projectId") Integer projectId);

    ProjectRequest findByProject_ProjectIdAndUser_Id(int projectId, int userId);

    List<ProjectRequest> findByUser_IdAndStatus(int userId, ProjectRequest.Status status);
    List<ProjectRequest> findByUserAndStatus(User  user, ProjectRequest.Status status);

    Integer countApprovedParticipantsByProject(Project project);

    Boolean existsByProject_ProjectIdAndUser_IdAndStatus(Integer projectId, Integer userId, ProjectRequest.Status status);

    List<ProjectRequest> findByUser(User user);

    @Query("SELECT pr.user FROM ProjectRequest pr WHERE pr.project.projectId = :projectId AND pr.status = :status")
    List<User> findUsersByProjectIdAndStatus(@Param("projectId") Integer projectId,
                                             @Param("status") ProjectRequest.Status status);
}