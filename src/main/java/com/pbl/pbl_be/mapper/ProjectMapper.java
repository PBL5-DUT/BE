package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

        // Gán PM từ pmId
        if (dto.getPmId() != null) {
            User pm = userRepository.findById(dto.getPmId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy PM với id: " + dto.getPmId()));
            project.setPm(pm);
        }

        // Gán project cha nếu có
        if (dto.getParentProjectId() != null) {
            Project parentProject = projectRepository.findById(dto.getParentProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy parent project với id: " + dto.getParentProjectId()));
            project.setParentProject(parentProject);
        }

        // Nếu bạn có status từ DTO thì xử lý thêm ở đây (giả sử là String)
        if (dto.getStatus() != null) {
            project.setStatus(Project.Status.valueOf(dto.getStatus()));
        }

        return project;
    }

    public ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();

        dto.setProjectId(project.getProjectId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setLocation(project.getLocation());
        dto.setAvatarFilepath(project.getAvatarFilepath());

        if (project.getParentProject() != null) {
            dto.setParentProjectId(project.getParentProject().getProjectId());
        }

        if (project.getPm() != null) {
            dto.setPmId(project.getPm().getUserId());
        }

        dto.setStartTime(project.getStartTime());
        dto.setEndTime(project.getEndTime());
        dto.setMaxParticipants(project.getMaxParticipants());

        if (project.getStatus() != null) {
            dto.setStatus(project.getStatus().name());
        }

        return dto;
    }
}
