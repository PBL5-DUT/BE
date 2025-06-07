package com.pbl.pbl_be.dto;

import com.pbl.pbl_be.model.Post.Status; // Import enum từ entity
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Integer postId;
    private Integer forumId;
    private Integer userId;
    private String userName;
    private String userAvatar;
    private String content;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isLiked;
    private int likeCount;
    private List<PostImageDTO> postImages;
}
