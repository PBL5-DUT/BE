package com.pbl.pbl_be.service;

import com.pbl.pbl_be.dto.ProjectLikeDTO;

public interface ProjectLikeService {
    void createProjectLike(Integer projectId, Integer userId);
    void deleteProjectLike(Integer projectId, Integer userId);


}
