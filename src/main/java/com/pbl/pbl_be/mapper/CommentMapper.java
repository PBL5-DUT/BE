package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.CommentDTO;
import com.pbl.pbl_be.model.Comment;
import com.pbl.pbl_be.repository.PostRepository;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentDTO toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setUserId(comment.getUser().getUserId());
        dto.setContent(comment.getContent());
        dto.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null);
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setPostId(comment.getPost().getPostId());
        dto.setUserName(comment.getUser().getUsername());
        dto.setAvatarFilePath(comment.getUser().getAvatarFilepath());
        return dto;
    }
    public Comment toEntity(CommentDTO dto) {
        if (dto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setCommentId(dto.getCommentId());
        comment.setPost(postRepository.findByPostId(dto.getPostId()));
        comment.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        comment.setContent(dto.getContent());
        if(dto.getParentCommentId()!= null) {
            comment.setParentComment(new Comment());
            comment.getParentComment().setCommentId(dto.getParentCommentId());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreatedAt(dto.getCreatedAt());
        return comment;
    }

}
