package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.repository.ForumRepository;
import com.pbl.pbl_be.repository.LikeRepository;
import com.pbl.pbl_be.repository.PostImageRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostMapper {



    @Autowired
    private PostImageMapper postImageMapper;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private LikeRepository likeRepository;// Mapper for post images

    public Post toEntity(PostDTO dto) {
        Post post = new Post();

        // Validate and set the forum
        post.setForum(forumRepository.findByForumId(dto.getForumId()));

        post.setPostId(dto.getPostId());
        post.setUser(userRepository.findByUserId(dto.getUserId()));
        post.setContent(dto.getContent());
        post.setStatus(dto.getStatus());
        post.setCreatedAt(dto.getCreatedAt());
        post.setUpdatedAt(dto.getUpdatedAt());
        return post;
    }

    public PostDTO toDTO(Post post)
    {
        return toDTO(post,0);
    }
    public PostDTO toDTO(Post post, int userId) {
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setForumId(post.getForum().getForumId());
        dto.setUserId(post.getUser().getUserId());
        dto.setUserName(post.getUser().getUsername());
        dto.setUserAvatar(post.getUser().getAvatarFilepath());
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        if(userId != 0) {
            dto.setIsLiked(likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId));
        } else {
            dto.setIsLiked(false);
        }

        dto.setLikeCount(likeRepository.countLikesByPost(post));

        dto.setPostImages(postImageRepository.findByPost_PostId(post.getPostId()).stream()
                .map(postImageMapper::toDTO)
                .collect(Collectors.toList()));

        return dto;
    }
}