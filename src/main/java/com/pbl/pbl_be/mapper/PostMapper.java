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
        post.setForum(forumRepository.findByForumId(dto.getForumId())); // Assuming Forum has a getId() method
        post.setPostId(dto.getPostId());
        post.setUser(userRepository.findByUserId(dto.getUserId()));  // Assuming User has a getId() method
        post.setContent(dto.getContent());
        post.setStatus(Post.Status.valueOf(dto.getStatus()));
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
        dto.setForumId(post.getForum().getForumId()); // Assuming Forum has a getId() method
        dto.setUserId(post.getUser().getUserId());  // Assuming User has a getId() method
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus().name());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        if(userId != 0) {
            dto.setIsLiked(likeRepository.existsByPostAndUser_UserId(post, userId));
        } else {
            dto.setIsLiked(false);
        }

        dto.setLikeCount(likeRepository.countLikesByPost(post)); // Assuming Post has a getLikes() method

        dto.setPostImages(postImageRepository.findByPost_PostId(post.getPostId()).stream()
                .map(postImageMapper::toDTO)
                .collect(Collectors.toList()));// Assuming Post has a getPostImages() method

        return dto;
    }
}