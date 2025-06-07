package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ProjectLikeDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.ProjectLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projectlikes")
public class ProjectLikeController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ProjectLikeService projectLikeService;

     @PostMapping("/{projectId}")
     public ResponseEntity<Void> likeProject(@PathVariable Integer projectId, @RequestHeader("Authorization") String token) {
         Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
         this.projectLikeService.createProjectLike(projectId, userId);
         return ResponseEntity.noContent().build();
     }

}
