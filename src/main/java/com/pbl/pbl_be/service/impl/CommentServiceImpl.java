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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    @Cacheable(value = "commentsByPost", key = "#postId")
    public List<CommentDTO> getCommentsByPostId(Integer postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);
        if (comments != null && !comments.isEmpty()) {
            return comments.stream()
                    .map(comment -> commentMapper.toDto(comment))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @CacheEvict(value = "commentsByPost", key = "#commentDTO.postId", allEntries = false)
    public void addComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }


    @Override
    @CacheEvict(value = "commentsByPost", key = "#commentDTO.postId")
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        commentRepository.deleteById(commentId);
    }

    @Override
    @CacheEvict(value = "commentsByPost", key = "#commentDTO.postId")
    public void updateComment( CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        if (comment != null && comment.getCommentId() != null) {
            comment.setCreatedAt(LocalDateTime.now()); // Update timestamp if needed
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment ID must not be null for update");
        }
    }
}