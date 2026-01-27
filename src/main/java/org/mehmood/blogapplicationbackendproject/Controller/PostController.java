package org.mehmood.blogapplicationbackendproject.Controller;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.mehmood.blogapplicationbackendproject.Service.FileService;
import org.mehmood.blogapplicationbackendproject.Service.PostService;
import org.mehmood.blogapplicationbackendproject.config.AppConstants;
import org.mehmood.blogapplicationbackendproject.payLoads.CustomApiResponse;
import org.mehmood.blogapplicationbackendproject.payLoads.PostDto;
import org.mehmood.blogapplicationbackendproject.payLoads.PostResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;


    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost
            (@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {

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
    public ResponseEntity<PostResponseDto> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) int pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) int pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
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

    // post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage
    (@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDto postById = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postById.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postById, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.CREATED);
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
