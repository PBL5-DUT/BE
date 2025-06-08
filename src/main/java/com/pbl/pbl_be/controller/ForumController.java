package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.ForumDTO;
import com.pbl.pbl_be.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getForumsByProjectId(
            @PathVariable Integer projectId,
            @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(this.forumService.getForumsByProjectId(projectId));
    }
    @PostMapping
    public ResponseEntity<?> createForum(
            @RequestBody @Valid ForumDTO forumDTO,
            @RequestHeader("Authorization") String token) {
        this.forumService.createForum(forumDTO);
        return ResponseEntity.ok("Create forum successfully");
    }
    @PutMapping("/{forumId}")
    public ResponseEntity<?> updateForum(
            @RequestBody @Valid ForumDTO forumDTO,
            @RequestHeader("Authorization") String token) {
        this.forumService.updateForum(forumDTO);
        return ResponseEntity.ok("Update forum successfully");
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<?> deleteForum(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token){
        this.forumService.deleteForum(forumId);
        return ResponseEntity.ok("Delete forum successfully");
    }

}
