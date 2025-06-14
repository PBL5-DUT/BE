package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ProjectDTO;
import com.pbl.pbl_be.exception.ResourceNotFoundException;
import com.pbl.pbl_be.mapper.ProjectMapper;
import com.pbl.pbl_be.model.*;
import com.pbl.pbl_be.repository.ProjectLikeRepository;
import com.pbl.pbl_be.repository.ProjectRepository;
import com.pbl.pbl_be.repository.ProjectRequestRepository;
import com.pbl.pbl_be.repository.ForumRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.repository.*;
import com.pbl.pbl_be.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Thêm import này

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ForumRepository forumRepo;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectLikeRepository projectLikeRepo;

    @Autowired
    private ProjectRequestRepository projectRequestRepo;

    @Override

    @Cacheable(value = "allProjects") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream()
                .map(project -> projectMapper.toDTO(project))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "projectDetails", key = "#projectId + '-' + #userId")
    public ProjectDTO getProjectById(Integer projectId, Integer userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        return projectMapper.toDTO(project, userId);
    }

    @Override
    @Cacheable(value = "projectsByPm", key = "#pmId") // Cache dự án theo PM
    public List<ProjectDTO> getProjectsByPmId(Integer pmId) {
        List<Project> projects = projectRepo.findProjectsByPmId(pmId);
        return projects.stream()
                .map(project -> projectMapper.toDTO(project))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"allProjects", "projectsByPm", "projectsByStatus", "projectsRemaining", "projectsLiked", "joinedProjects", "childProjects", "userProjects", "projectDetails"}, allEntries = true)
    public ProjectDTO createProject(ProjectDTO projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setStatus(Project.Status.pending);
        Project savedProject = this.projectRepo.save(project);


        Forum forum = new Forum();
        forum.setProject(project);
        forum.setCreatedAt(LocalDateTime.now());
        forum.setTitle("Ban tổ chức");
        this.forumRepo.save(forum);


        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setProject(savedProject);
        User pm = this.userRepo.findById(project.getPm().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", project.getPm().getUserId()));
        projectRequest.setUser(pm);
        this.projectRequestRepo.save(projectRequest);

        return projectMapper.toDTO(savedProject);
    }

    @Override

    @CachePut(value = "projectDetails", key = "#projectId + '-' + #projectDto.pmId") // Cập nhật cache chi tiết dự án sau khi update
    @CacheEvict(value = {"allProjects", "projectsByPm", "projectsByStatus", "projectsRemaining", "projectsLiked", "joinedProjects", "childProjects", "userProjects", }, allEntries = true)
    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDto) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStartTime(projectDto.getStartTime());
        project.setEndTime(projectDto.getEndTime());
        project.setStatus(Project.Status.valueOf(projectDto.getStatus()));
        project.setMaxParticipants(projectDto.getMaxParticipants());
        project.setBank(projectDto.getBank());
        project.setAvatarFilepath(projectDto.getAvatarFilepath());
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override

    @Cacheable(value = "projectsByStatus", key = "#sort + '-' + #userId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getProjectsByStatusSorted(String sort, Integer userId) {
        List<Project.Status> desiredStatuses = Arrays.asList(
                Project.Status.approved,
                Project.Status.finished,
                Project.Status.locked,
                Project.Status.lockedpending
        );
        List<Project> projects = this.projectRepo.findByStatusIn(desiredStatuses);
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());

        Comparator<ProjectDTO> comparator;
        switch (sort) {
            case "startTime":
                comparator = Comparator.comparing(ProjectDTO::getStartTime);
                break;
            case "likesCount":
                comparator = Comparator.comparing(ProjectDTO::getLikesCount);
                break;
            case "participantsCount":
                comparator = Comparator.comparing(ProjectDTO::getParticipantsCount);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field!");
        }

        projectDTOs.removeIf(projectDTO ->
                projectDTO.getPmId() != null && projectDTO.getPmId().equals(userId)
        );

        projectDTOs.sort(comparator.reversed());
        return projectDTOs;
    }

    @Override

    @Cacheable(value = "projectsRemaining", key = "#userId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getProjectsByStatusRemaining(Integer userId) {
        List<Project.Status> desiredStatuses = Arrays.asList(
                Project.Status.approved,
                Project.Status.finished,
                Project.Status.locked,
                Project.Status.lockedpending
        );
        List<Project> approvedProjects = this.projectRepo.findByStatusIn(desiredStatuses);

        LocalDateTime now = LocalDateTime.now();
        List<ProjectDTO> projectDTOs = approvedProjects.stream()
                .filter(project -> project.getEndTime().isAfter(now.toLocalDate()))
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());

        projectDTOs.removeIf(projectDTO ->
                projectDTO.getPmId() != null && projectDTO.getPmId().equals(userId)
        );

        projectDTOs.sort(Comparator.comparing(ProjectDTO::getEndTime));
        return projectDTOs;
    }

    @Override

    @Cacheable(value = "projectsLiked", key = "#userId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getProjectsLiked(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<ProjectLike> projectLikes = this.projectLikeRepo.findProjectLikesByUser(user);
        List<Project> returnProjects = new ArrayList<>();

        for (ProjectLike projectLike : projectLikes) {
            Project project = projectLike.getProject();
            if (project.getPm().getUserId().equals(userId)) {
                continue;
            }
            returnProjects.add(project);
        }
        List<ProjectDTO> projectDTOs = returnProjects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());
        projectDTOs.removeIf(projectDTO ->
                      !projectDTO.getStatus().equals("approved") &&
                        !projectDTO.getStatus().equals("finished") &&
                        !projectDTO.getStatus().equals("locked") &&
                        !projectDTO.getStatus().equals("lockedpending")
        );

        return projectDTOs;
    }

    @Override

    @Cacheable(value = "joinedProjects", key = "#userId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getJoinedProjects(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Project> projects = this.projectRequestRepo.findByUserAndStatus(user, ProjectRequest.Status.approved)
                .stream()
                .map(ProjectRequest::getProject)
                .collect(Collectors.toList());

        List<ProjectDTO> projectDTOs = projects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());


        projectDTOs.removeIf(projectDTO ->
                        !projectDTO.getStatus().equals("approved") &&
                        !projectDTO.getStatus().equals("finished") &&
                        !projectDTO.getStatus().equals("locked") &&
                        !projectDTO.getStatus().equals("lockedpending")

        );
        return projectDTOs;

    }

    @Override

    @Cacheable(value = "childProjects", key = "#parentProjectId + '-' + #userId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getChildProjectsByParentId(Integer parentProjectId, Integer userId) {
        List<Project> childProjects = this.projectRepo.findByParentProject_ProjectIdAndStatus(parentProjectId, Project.Status.approved);

        return childProjects.stream()
                .map(project -> projectMapper.toDTO(project, userId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional // Để đảm bảo giao dịch
    @CacheEvict(value = {"projectDetails", "allProjects", "projectsByStatus", "projectsRemaining", "projectsLiked"}, allEntries = true)
    public void likeProject(Integer projectId, Integer userId) {
        if (this.projectLikeRepo.existsByProject_ProjectIdAndUser_Id(projectId, userId)) {
            this.projectLikeRepo.deleteByProject_ProjectIdAndUser_Id(projectId, userId);
        } else {
            Project project = this.projectRepo.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
            User user = this.userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

            ProjectLike like = new ProjectLike();
            like.setProject(project);
            like.setUser(user);

            this.projectLikeRepo.save(like);
        }
    }

    @Override
    @Cacheable(value = "userProjects", key = "#userId + '-' + #currentUserId") // Tên cache rõ ràng hơn
    public List<ProjectDTO> getProjectsByUserId(Integer userId, Integer currentUserId) {
        List<Project> projects = this.projectRequestRepo.findByUser_IdAndStatus(userId, ProjectRequest.Status.approved)
                .stream()
                .map(ProjectRequest::getProject)
                .collect(Collectors.toList());
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(project -> projectMapper.toDTO(project, currentUserId))
                .collect(Collectors.toList());
        return projectDTOs;
    }

    @Override

    @CacheEvict(value = {"allProjects", "projectsByPm", "projectsByStatus", "projectsRemaining", "projectsLiked", "joinedProjects", "childProjects", "userProjects", "projectDetails"}, allEntries = true)
    public void deleteProject(Integer projectId) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        this.projectRepo.delete(project);
    }

    @Override
    @CachePut(value = "projectDetails", key = "#projectId + '-' + #projectDto.pmId")
    @CacheEvict(value = {"projectsByPm", "allProjects", "projectsByStatus", "projectsRemaining", "projectsLiked", "joinedProjects", "childProjects"}, allEntries = true)
    public ProjectDTO lockProject(Integer projectId, ProjectDTO projectDto) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));

        project.setStatus(Project.Status.valueOf(projectDto.getStatus()));
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    @Override
    @CacheEvict(value = {"projectsByPm", "allProjects", "childProjects", "projectDetails"}, allEntries = true) // Xóa các cache liên quan khi sao chép dự án
    public ProjectDTO copyProject(Project projectId, ProjectDTO projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setParentProject(projectId);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setStatus(Project.Status.draft);
        Project savedProject = this.projectRepo.save(project);
        return projectMapper.toDTO(savedProject);
    }
}