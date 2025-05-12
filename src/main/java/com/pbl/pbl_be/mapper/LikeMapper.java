package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.LikeDTO;
import com.pbl.pbl_be.model.Like;

import com.pbl.pbl_be.repository.PostRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeMapper {
    @Autowired
    private static PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public  LikeDTO toDto(Like like) {
        if (like == null) {
            return null;
        }
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(like.getPost().getPostId());
        likeDTO.setUserId(like.getUser().getUserId());
        return likeDTO;
    }

    public  Like toEntity(LikeDTO likeDTO) {
        if (likeDTO == null) {
            return null;
        }
        Like like = new Like();
        like.setPost(postRepository.findByPostId(likeDTO.getPostId()));
        like.setUser(userRepository.findByUserId(likeDTO.getUserId()));
        like.setCreatedAt(LocalDateTime.now());
        return like;
    }
}
