package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.model.Post;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private PostService postService;

    @GetMapping("/{forumId}")
    public ResponseEntity<PostDTO> getPostsByForumId(
            @PathVariable Integer forumId,
            @RequestHeader("Authorization") String token) {
        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
return ResponseEntity.ok((PostDTO) postService.getPostsByForumIdAndStatus(forumId, Post.Status.approved.name()));
        }

    @PostMapping()
public ResponseEntity<String> createPost(
        @RequestBody @Valid PostDTO postDto,
        @RequestHeader("Authorization") String token) {
    Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
    postService.createPost(postDto);
    return ResponseEntity.ok("Post created successfully");
}




}
