package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.PostImageDTO;
import com.pbl.pbl_be.mapper.PostMapper;
import com.pbl.pbl_be.model.Like;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.model.PostImage;
import com.pbl.pbl_be.repository.*;
import com.pbl.pbl_be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.pbl.pbl_be.model.Report.ReportType.post;

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
 public List<PostDTO> getPostsByForumIdAndStatus(Integer forumId, Post.Status status, Integer userId) {

     List<Post> posts = postRepository.findByForum_ForumIdAndStatus(forumId, status);

     return posts.stream()
             .map(post -> postMapper.toDTO(post, userId)) // Assuming 0 is the userId for the current user
             .collect(Collectors.toList());
 }

    @Override
    public void createPost(PostDTO postDto) {
        Post post=new Post();
        System.out.println("postDto.getForumId() = " + postDto.getForumId());
        System.out.println("postDto.getUserId() = " + postDto.getUserId());
        post.setForum(forumRepository.findByForumId(postDto.getForumId()));
        post.setUser(userRepository.findByUserId(postDto.getUserId()));
        post.setContent(postDto.getContent());
        post.setStatus(Post.Status.pending);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);

        for(PostImageDTO postImageDTO : postDto.getPostImages()) {
            System.out.println("postImageDTO.getImageFilepath() = " + postImageDTO.getImageFilepath());
        }

        List<PostImage> postImages = postDto.getPostImages().stream()
                .map(imageDto -> {
                    PostImage postImage = new PostImage();
                    postImage.setPost(post);
                    postImage.setImageFilepath(imageDto.getImageFilepath()); // Save the file path// Save the file path
                    return postImage;
                })
                .collect(Collectors.toList());
        postImageRepository.saveAll(postImages);

    }

    @Override
    public void likePost(Integer postId, Integer userId) {
       if (likeRepository.existsByPost_PostIdAndUser_UserId(postId, userId)) {
            // If the user already liked the post, remove the like
            likeRepository.deleteByPost_PostIdAndUser_UserId(postId, userId);
        } else {
            // If the user has not liked the post, add a new like
likeRepository.save(new Like(
    postRepository.findById(postId.longValue()).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId)),
    userRepository.findById((int) userId.longValue()).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId))
));    }

}
}
