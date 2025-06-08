package com.pbl.pbl_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer postId;
    private Integer userId;
}
