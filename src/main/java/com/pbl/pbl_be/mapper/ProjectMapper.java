package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.ProjectRequest;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectLikeRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ProjectMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRequestRepository projectRequestRepository;

    @Autowired
    private ProjectLikeRepository projectLikeRepository;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    public Project toEntity(ProjectDTO dto) {
        Project project = new Project();

        project.setProjectId(dto.getProjectId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setLocation(dto.getLocation());
        project.setAvatarFilepath(dto.getAvatarFilepath());
        project.setStartTime(dto.getStartTime());
        project.setEndTime(dto.getEndTime());
        project.setMaxParticipants(dto.getMaxParticipants());
        project.setBank(dto.getBank());

        if (dto.getPmId() != null) {
            User pm = userRepository.findById(dto.getPmId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy PM với id: " + dto.getPmId()));
            project.setPm(pm);
        }

        if (dto.getParentProjectId() != null) {
            Project parentProject = projectRepository.findById(dto.getParentProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy parent project với id: " + dto.getParentProjectId()));
            project.setParentProject(parentProject);
        }

        if (dto.getStatus() != null) {
            project.setStatus(Project.Status.valueOf(dto.getStatus()));
        }

        return project;
    }
    public ProjectDTO toDTO(Project project) {
        return toDTO(project, -1);
    }

    public ProjectDTO toDTO(Project project, int userId) {
        ProjectDTO dto = new ProjectDTO();

        dto.setProjectId(project.getProjectId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setLocation(project.getLocation());
        dto.setAvatarFilepath(project.getAvatarFilepath());
        dto.setBank(project.getBank());

        if (project.getParentProject() != null) {
            dto.setParentProjectId(project.getParentProject().getProjectId());
        }

        if (project.getPm() != null) {
            dto.setPmId(project.getPm().getUserId());
        }

        dto.setStartTime(project.getStartTime());
        dto.setEndTime(project.getEndTime());
        dto.setMaxParticipants(project.getMaxParticipants());

        dto.setParticipantsCount(projectRequestRepository.countApprovedParticipantsByProject(project));
        dto.setLikesCount(projectLikeRepository.countProjectLikeByProject(project));

        if (project.getStatus() != null) {
            dto.setStatus(project.getStatus().name());
        }

        if(userId>0) {
            // Kiểm tra isLiked
            dto.setIsLiked(projectLikeRepository.existsByProject_ProjectIdAndUser_Id(project.getProjectId(), userId));

            // Kiểm tra hasJoined
            dto.setHasJoined(projectRequestRepository.existsByProject_ProjectIdAndUser_IdAndStatus(
                    project.getProjectId(), userId, ProjectRequest.Status.approved));
        }
        return dto;
    }
}