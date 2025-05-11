package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Forum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ForumDTO {
    private Integer forumId;
    private Integer projectId;
    private String title;

    public ForumDTO(Forum forum) {
        this.forumId = forum.getForumId();
        this.projectId = forum.getProject().getProjectId();
        this.title = forum.getTitle();
    }

    public ForumDTO() {  }
}
