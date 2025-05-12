package com.pbl.pbl_be.repository;
import com.pbl.pbl_be.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Integer> {
    List<Forum> findByProject_ProjectId(int projectId);

    Forum findByForumId(Integer forumId);
}
