package com.pbl.pbl_be.service.impl;


import com.pbl.pbl_be.dto.CommentDTO;
import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.ReportDTO;
import com.pbl.pbl_be.mapper.CommentMapper;
import com.pbl.pbl_be.mapper.PostMapper;
import com.pbl.pbl_be.model.Comment;
import com.pbl.pbl_be.model.Forum;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.model.Report;
import com.pbl.pbl_be.repository.*;
import com.pbl.pbl_be.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ForumRepository forumRepository; // Assuming you have a ForumRepository for forums

    @Autowired
    private CommentRepository commentRepository; // Assuming you have a CommentRepository for comments

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper; // Assuming you have a CommentMapper for comments

    @Override
    @CacheEvict(value = "reports", allEntries = true) // Invalidate all report caches when a new report is added
    public void addReport(ReportDTO reportDTO, int userId) {
        Report report = new Report();
        report.setReportItemId(Long.valueOf(reportDTO.getReportItemId()));
        report.setReportType(Report.ReportType.valueOf(reportDTO.getReportType()));
        report.setReason(reportDTO.getReason());
        report.setCreatedAt(java.time.LocalDateTime.now());
        report.setStatus(Report.ReportStatus.pending);
        report.setUser(userRepository.findByUserId(userId));
        if (report.getUser() == null) {
            throw new RuntimeException("User not found");
        }
        this.reportRepository.save(report);
    }



    public List<PostDTO> getPendingPostReports(int projectId) {
        List<Report> reports = reportRepository.findByReportTypeAndStatusOrderByCreatedAtDesc(Report.ReportType.post, Report.ReportStatus.pending);

        List<Forum> forums = forumRepository.findByProject_ProjectId(projectId);
        Forum forum = null;
        if (forums != null && !forums.isEmpty()) {
            forum = forums.get(0);
        }
        if (reports != null && !reports.isEmpty()) {
            List<Post> posts = new ArrayList<>();
            for (Report report : reports) {
                Post post = postRepository.findByPostId(report.getReportItemId().intValue());
                if (post != null&& post.getForum() != null && post.getForum().getForumId() == forum.getForumId()) {
                    posts.add(post);
                }
            }
             return posts.stream()
                    .map(post -> postMapper.toDTO(post, 0)) // Assuming 0 is the userId for the current user
                    .collect(Collectors.toList());

        }
        return null;
    }

    public List<CommentDTO> getPendingCommentReports(int projectId) {
        List<Report> reports = reportRepository.findByReportTypeAndStatusOrderByCreatedAtDesc(Report.ReportType.comment, Report.ReportStatus.pending);
        List<Forum> forums = forumRepository.findByProject_ProjectId(projectId);
        Forum forum = null;
        if (forums != null && !forums.isEmpty()) {
            forum = forums.get(0);
        }
        if (reports != null && !reports.isEmpty()) {
            List<Comment> comments = new ArrayList<>();
            for (Report report : reports) {
                Comment comment = commentRepository.findByCommentId(report.getReportItemId().intValue());
                if (comment != null&& comment.getPost() != null && comment.getPost().getForum() != null && comment.getPost().getForum().getForumId() == forum.getForumId()) {
                    comments.add(comment);
                }
            }
            return comments.stream()
                    .map(comment -> commentMapper.toDto(comment))
                    .collect(Collectors.toList());

        }
        return null;
    }


    @Override
    public void resolveReport(int reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(Report.ReportStatus.resolved);
        reportRepository.save(report);
    }

    @Override
    public void dismissReport(int reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        reportRepository.delete(report);
    }

}
