package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.PostImageDTO;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private PostService postService;

    @GetMapping("/{forumId}")
    public List<PostDTO> getPostsByForumId(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token) {
return postService.getPostsByForumIdAndStatus(forumId, Post.Status.approved);
    }

    @PostMapping()
public ResponseEntity<String> createPost(
        @RequestBody @Valid PostDTO postDto,
        @RequestHeader("Authorization") String token) {
        try{
    postService.createPost(postDto);
    return ResponseEntity.ok("Post created successfully");
} catch (Exception e) {
        return ResponseEntity.status(500).body("Error creating post: " + e.getMessage());
    }}

    @PostMapping("/{postId}/like")
public ResponseEntity<String> likePost(
        @PathVariable Integer postId,
        @RequestHeader("Authorization") String token) {
    Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
    try {
        postService.likePost(postId, userId);
        return ResponseEntity.ok("Post liked successfully");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error liking post: " + e.getMessage());
    }

}}
