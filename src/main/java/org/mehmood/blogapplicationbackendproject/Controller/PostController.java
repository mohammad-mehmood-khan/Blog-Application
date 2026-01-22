package org.mehmood.blogapplicationbackendproject.Controller;


import jakarta.validation.Valid;
import org.mehmood.blogapplicationbackendproject.Service.PostService;
import org.mehmood.blogapplicationbackendproject.config.AppConstants;
import org.mehmood.blogapplicationbackendproject.payLoads.CustomApiResponse;
import org.mehmood.blogapplicationbackendproject.payLoads.PostDto;
import org.mehmood.blogapplicationbackendproject.payLoads.PostResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {

        return new ResponseEntity<>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<CustomApiResponse> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new CustomApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponseDto> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER) int pageNumber
            , @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE)int pageSize
            ,@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy
    ,@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR)String sortDir)
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(postService.getPostByCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(postService.getPostByUser(userId), HttpStatus.OK);

    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@RequestParam String keyword) {

        return ResponseEntity.ok(postService.searchPostsByTitle(keyword));
    }
    //searching

}
