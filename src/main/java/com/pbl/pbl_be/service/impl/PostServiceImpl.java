package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {

    @Override
    public List<Post> getPostsByForumIdAndStatus(Integer forumId, String status) {
        return List.of();
    }

    @Override
    public void createPost(PostDTO postDto, Integer userId) {

    }

}
