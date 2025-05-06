package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getPostsByForumIdAndStatus(Integer forumId, String status);
    void createPost(PostDTO postDto, Integer userId);
}
