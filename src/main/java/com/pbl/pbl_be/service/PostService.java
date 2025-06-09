package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.model.Post;

import java.util.List;

public interface PostService {
    List<PostDTO> getPostsByForumIdAndStatus(Integer forumId, Post.Status status, Integer userId);
    void createPost(PostDTO postDto);

    void likePost(Integer postId, Integer userId);
    void approvePost(Integer postId);
    void rejectPost(Integer postId);
}
