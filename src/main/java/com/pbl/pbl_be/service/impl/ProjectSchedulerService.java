package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.model.Project;
import com.pbl.pbl_be.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectSchedulerService {

    private final ProjectRepository projectRepository;

    @Transactional
    public void updateExpiredProjectsStatus() {
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Tìm các project đã hết hạn
            List<Project> expiredProjects = projectRepository.findExpiredProjects(
                    currentDate, Project.Status.finished
            );

            if (!expiredProjects.isEmpty()) {
                log.info("Found {} expired projects to update", expiredProjects.size());

                // Cách 1: Cập nhật từng project (để có thể log chi tiết)
                for (Project project : expiredProjects) {
                    Project.Status oldStatus = project.getStatus();
                    project.setStatus(Project.Status.finished);
                    project.setUpdatedAt(currentDateTime);
                    projectRepository.save(project);

                    log.info("Updated project ID: {} '{}' from {} to finished",
                            project.getProjectId(), project.getName(), oldStatus);
                }

                // Cách 2: Cập nhật batch (hiệu quả hơn cho số lượng lớn)
                // int updatedCount = projectRepository.updateExpiredProjectsStatus(
                //     currentDate, Project.Status.finished, currentDateTime
                // );
                // log.info("Updated {} expired projects to finished status", updatedCount);

            } else {
                log.info("No expired projects found to update");
            }

        } catch (Exception e) {
            log.error("Error occurred while updating expired projects status", e);
            throw e; // Re-throw để transaction rollback
        }
    }
}
