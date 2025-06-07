package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Like;
import com.pbl.pbl_be.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT COUNT(pl) FROM Like pl WHERE pl.post = :post")
    int countLikesByPost(@Param("post") Post post);


    Boolean existsByPost_PostIdAndUser_UserId(int postId, int userId);

    void deleteByPost_PostIdAndUser_UserId(int postId, int userId);
}
