package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.PostImageDTO;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.model.PostImage;
import com.pbl.pbl_be.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostImageMapper {
    @Autowired
    private PostRepository postRepository;

    public PostImageDTO toDTO(PostImage postImage) {
        PostImageDTO postImageDTO = new PostImageDTO();
        postImageDTO.setImageId(postImage.getImageId());
        postImageDTO.setPostId(postImage.getPost().getPostId());
        postImageDTO.setImageFilepath(postImage.getImageFilepath());
        postImageDTO.setCreatedAt(postImage.getCreatedAt());
        return postImageDTO;
    }

    public PostImage toEntity(PostImageDTO postImageDTO) {
        PostImage postImage = new PostImage();
        postImage.setImageFilepath(postImageDTO.getImageFilepath());
        postImage.setCreatedAt(postImageDTO.getCreatedAt());
        postImage.setPost(postRepository.findById(Long.valueOf(postImageDTO.getPostId()))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bài viết với id: " + postImageDTO.getPostId())));
        return postImage;
    }
}
