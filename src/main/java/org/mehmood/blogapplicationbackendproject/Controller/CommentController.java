package org.mehmood.blogapplicationbackendproject.Controller;

import jakarta.validation.Valid;
import org.mehmood.blogapplicationbackendproject.Service.CommentService;
import org.mehmood.blogapplicationbackendproject.payLoads.CommentDto;
import org.mehmood.blogapplicationbackendproject.payLoads.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/users/{userId}/comments")
    public ResponseEntity<CommentDto> createComment
            (@Valid @RequestBody CommentDto commentDto,
             @PathVariable Integer postId,
             @PathVariable Integer userId) {
        CommentDto comment = this.commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<CustomApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>
                (new CustomApiResponse("comment deleted successfully", true), HttpStatus.OK);
    }
}
