package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.PostDTO;
import com.pbl.pbl_be.security.JwtTokenHelper;
import com.pbl.pbl_be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

//    @Autowired
//    private PostService postService;

//    @GetMapping("/{forumId}")
//    public ResponseEntity<PostDTO> getPostsByForumId(
//            @PathVariable Integer forumId,
//            @RequestHeader("Authorization") String token) {
//        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
//        return ResponseEntity.ok(postService.getPostsByForumIdAndStatus(forumId, userId));
//    }

//    @PostMapping()
//    public ResponseEntity<PostDTO> createPost(
//            @RequestBody PostDTO postDto,
//            @RequestHeader("Authorization") String token) {
//        Integer userId = jwtTokenHelper.getUserIdFromToken(token.substring(7));
//        return ResponseEntity.ok(postService.createPost(postDto);
//    }




}
