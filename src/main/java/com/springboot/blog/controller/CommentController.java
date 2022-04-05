package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getAllComments(@PathVariable(name = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/{postId}/comments/{commentid}")
    public CommentDto getCommentById(@PathVariable(name = "postId") Long postId,
                                     @PathVariable(name = "commentid") Long commentid){
        return commentService.getCommentById(postId, commentid);
    }

    @PutMapping("posts/{postId}/Comments/{commentId}")
    public CommentDto updateComment(@PathVariable(name = "postId") Long postId,
                                   @PathVariable(name = "commentid") Long commentid,
                                   @Valid @RequestBody CommentDto commentDto){
        return commentService.updateComment(postId, commentid, commentDto);
    }

    @DeleteMapping("posts/{postId}/Comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "commentid") Long commentid) {
        commentService.deleteComment(postId, commentid);
        return new ResponseEntity<>("Comment has been deleted successfully", HttpStatus.OK);
    }
}
