package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.dto.PostImageDTO;
import com.pbl.pbl_be.dto.UserDTO;
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
    public ResponseEntity<List<PostDTO>> getPostsByForumId(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token) {

        int userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(
                postService.getPostsByForumIdAndStatus(forumId, Post.Status.approved, userId));
    }

    @PostMapping()
    public ResponseEntity<Void> createPost(
            @RequestBody @Valid PostDTO postDto,
            @RequestHeader("Authorization") String token) {
        postService.createPost(postDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String token) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();

    }
    
    @GetMapping("/{forumId}/pending")
    public List<PostDTO> getPendingPosts(@PathVariable Integer forumId, @RequestHeader("Authorization") String token) {
        return postService.getPostsByForumIdAndStatus(forumId, Post.Status.pending);
    }

  
  
    @PutMapping("/{forumId}/approve/{postId}")
        public ResponseEntity<?> approvePost(
                @PathVariable Integer forumId,
                @PathVariable Integer postId) {
            try {
                postService.approvePost(postId);
                return ResponseEntity.ok().body("Post approved successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PutMapping("/{forumId}/reject/{postId}")
        public ResponseEntity<?> rejectPost(
                @PathVariable Integer forumId,
                @PathVariable Integer postId) {
            try {
                postService.rejectPost(postId);
                return ResponseEntity.badRequest().body("Post rejected successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
