package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
