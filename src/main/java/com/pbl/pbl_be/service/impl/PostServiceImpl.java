package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.PostImageDTO;
import com.pbl.pbl_be.mapper.PostMapper;
import com.pbl.pbl_be.model.*;
import com.pbl.pbl_be.repository.*;
import com.pbl.pbl_be.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;



    @Override
    @Cacheable(value = "postsByForumAndStatus", key = "#forumId + '-' + #status + '-' + #userId")
    public List<PostDTO> getPostsByForumIdAndStatus(Integer forumId, Post.Status status, Integer userId) {
        List<Post> posts = postRepository.findByForum_ForumIdAndStatus(forumId, status);
        return posts.stream()
                .map(post -> postMapper.toDTO(post, userId))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "postsByForumAndStatus", allEntries = true) // Xóa toàn bộ cache liên quan đến bài đăng khi có bài mới
    public void createPost(PostDTO postDto) {
        Post post = new Post();
        System.out.println("forum"+postDto.getForumId());
        post.setForum(forumRepository.findByForumId(postDto.getForumId()));
        post.setUser(userRepository.findByUserId(postDto.getUserId()));
        post.setContent(postDto.getContent());
        post.setStatus(Post.Status.pending);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);

        List<PostImage> postImages = postDto.getPostImages().stream()
                .map(imageDto -> {
                    PostImage postImage = new PostImage();
                    postImage.setPost(post);
                    postImage.setImageFilepath(imageDto.getImageFilepath());
                    return postImage;
                })
                .collect(Collectors.toList());
        postImageRepository.saveAll(postImages);
    }

    @Transactional
    @Override
    @CacheEvict(value = "postsByForumAndStatus", allEntries = true) // Xóa toàn bộ cache khi có tương tác like/unlike
    public void likePost(Integer postId, Integer userId) {
        if (likeRepository.existsByPost_PostIdAndUser_UserId(postId, userId)) {
            likeRepository.deleteByPost_PostIdAndUser_UserId(postId, userId);
        } else {
     likeRepository.save(new Like(
                    postRepository.findById(postId.longValue()).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId)),
                    userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId))
            ));
        }

}

@Override

@CacheEvict(value = "postsByForumAndStatus", allEntries = true)
public void approvePost(Integer postId) {
    Post post = postRepository.findByPostId(postId);
    if (post == null) {
        throw new RuntimeException("Post not found");
 }
    }


@Override

@CacheEvict(value = "postsByForumAndStatus", allEntries = true)
public void rejectPost (Integer postId) {
    Post post = postRepository.findByPostId(postId);
    if (post == null) {
        throw new RuntimeException("Post not found");
    }
    post.setStatus(Post.Status.rejected);
    post.setUpdatedAt(LocalDateTime.now());
    postRepository.save(post);
}

    @Override
    public List<PostDTO> getPendingPosts(Integer forumId, Post.Status status) {
        List<Post> posts = postRepository.findByForum_ForumIdAndStatus(forumId, status);


        return posts.stream()
                .map(post -> postMapper.toDTO(post, 0)) // Assuming 0 is the userId for the current user
                .collect(Collectors.toList());
    }

}