package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_images")
@Getter @Setter
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(length = 1000)
    private String imageFilepath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
