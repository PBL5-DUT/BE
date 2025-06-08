package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.CommentDTO;
import com.pbl.pbl_be.mapper.CommentMapper;
import com.pbl.pbl_be.model.Comment;
import com.pbl.pbl_be.repository.CommentRepository;
import com.pbl.pbl_be.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;// Assuming you have a CommentRepository for database operations


    @Override
//    @Cacheable(value = "comments", key = "#postId")
    public List<CommentDTO> getCommentsByPostId(Integer postId) {
        List<Comment> comments= commentRepository.findByPost_PostId(postId);
        if (comments != null && !comments.isEmpty()) {
            return comments.stream()
                    .map(comment -> commentMapper.toDto(comment)) // Assuming 0 is the userId for the current user
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Cacheable(value = "comments", key = "#commentId")
    public void addComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        if (comment != null) {
            commentRepository.save(comment);
        }
    }



    @Override
    @CacheEvict(value = "comments", key = "#commentId")
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @CachePut(value = "comments", key = "#commentDTO.commentId")
    public void updateComment( CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        if (comment != null && comment.getCommentId() != null) {
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment ID must not be null for update");
        }

    }
}
