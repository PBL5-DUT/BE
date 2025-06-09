package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ForumDTO;
import com.pbl.pbl_be.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping("/{forumId}")
    public ResponseEntity<ForumDTO> getForumByForumId(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token){
        System.out.println("Token: " + token);
        return ResponseEntity.ok(this.forumService.getForumByForumId(forumId));
    }


    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ForumDTO>> getForumsByProjectId(
            @PathVariable Integer projectId,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(this.forumService.getForumsByProjectId(projectId));
    }
    @PostMapping
    public ResponseEntity<Void> createForum(
            @RequestBody @Valid ForumDTO forumDTO,
            @RequestHeader("Authorization") String token) {
        this.forumService.createForum(forumDTO);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{forumId}")
    public ResponseEntity<Void> updateForum(
            @RequestBody @Valid ForumDTO forumDTO,
            @RequestHeader("Authorization") String token) {
        this.forumService.updateForum(forumDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<?> deleteForum(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token){
        this.forumService.deleteForum(forumId);
        return ResponseEntity.ok().build();
    }

}
