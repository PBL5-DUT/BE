package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostImageDTO {
    private Integer imageId;
    private Integer postId;
    private String imageFilepath;
    private LocalDateTime createdAt;


}
