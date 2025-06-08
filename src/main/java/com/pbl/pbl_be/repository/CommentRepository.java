package com.pbl.pbl_be.repository;


import com.pbl.pbl_be.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost_PostId(Integer postId);

}
