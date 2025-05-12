package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Integer postId);

    List<Post> findByForum_ForumIdAndStatus(Integer forumId, String status);
}
