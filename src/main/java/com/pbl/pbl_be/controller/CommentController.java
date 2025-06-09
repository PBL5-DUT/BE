package com.pbl.pbl_be.controller;


import com.pbl.pbl_be.dto.CommentDTO;
import com.pbl.pbl_be.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @PostMapping("/{postId}")
    public ResponseEntity<Void> addComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer postId,
            @RequestBody CommentDTO commentDTO) {
         this.commentService.addComment( commentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer commentId,
            @RequestBody CommentDTO commentDTO) {
        this.commentService.updateComment(commentDTO);
        return ResponseEntity.ok().build();
    }

}
