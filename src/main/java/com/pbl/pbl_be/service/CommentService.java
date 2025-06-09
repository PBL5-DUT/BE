package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentsByPostId(Integer postId);

    void addComment(CommentDTO commentDTO);

    void deleteComment(Integer commentId);

    void updateComment( CommentDTO commentDTO);
}
