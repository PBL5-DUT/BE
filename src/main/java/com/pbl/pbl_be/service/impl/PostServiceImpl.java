package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.mapper.PostMapper;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.repository.PostRepository;
import com.pbl.pbl_be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<PostDTO> getPostsByForumIdAndStatus(Integer forumId, String status) {
        List<Post> posts=postRepository.findByForum_ForumIdAndStatus(forumId, status);
        return posts.stream().map(postMapper::toDTO).toList();

    }

    @Override
    public void createPost(PostDTO postDto) {
        Post post = postMapper.toEntity(postDto);
        post.setStatus(Post.Status.pending);
        postRepository.save(post);

    }

}
