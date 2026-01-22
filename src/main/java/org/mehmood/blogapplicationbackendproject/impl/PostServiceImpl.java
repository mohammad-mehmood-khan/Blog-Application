package org.mehmood.blogapplicationbackendproject.impl;

import org.mehmood.blogapplicationbackendproject.Repository.CategoryRepo;
import org.mehmood.blogapplicationbackendproject.Repository.PostRepo;
import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.mehmood.blogapplicationbackendproject.Service.PostService;
import org.mehmood.blogapplicationbackendproject.entity.Category;
import org.mehmood.blogapplicationbackendproject.entity.Post;
import org.mehmood.blogapplicationbackendproject.entity.User;
import org.mehmood.blogapplicationbackendproject.exceptions.ResourceNotFoundException;
import org.mehmood.blogapplicationbackendproject.payLoads.CategoryDto;
import org.mehmood.blogapplicationbackendproject.payLoads.PostDto;
import org.mehmood.blogapplicationbackendproject.payLoads.PostResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    public PostServiceImpl(PostRepo postRepo, ModelMapper modelMapper, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    // ---------------- CREATE ----------------
    @Override
    @Transactional
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post = dtoToPost(postDto);
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepo.save(post);
        return postToDto(savedPost);
    }

    // ---------------- UPDATE ----------------
    @Override
    @Transactional
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);
        return postToDto(updatedPost);
    }

    // ---------------- DELETE ----------------
    @Override
    @Transactional
    public void deletePost(Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        postRepo.delete(post);
    }

    // ---------------- GET ALL (Pagination + Sorting) ----------------
    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(pageable);

        List<PostDto> postDtos = pagePost.getContent().stream().map(this::postToDto).collect(Collectors.toList());

        PostResponseDto response = new PostResponseDto();
        response.setContent(postDtos);
        response.setPageNumber(pagePost.getNumber());
        response.setPageSize(pagePost.getSize());
        response.setTotalElements(pagePost.getTotalElements());
        response.setTotalPages(pagePost.getTotalPages());
        response.setLastPage(pagePost.isLast());

        return response;
    }

    // ---------------- GET BY ID ----------------
    @Override
    @Transactional(readOnly = true)
    public PostDto getPostById(Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        return postToDto(post);
    }

    // ---------------- GET BY CATEGORY ----------------
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        return postRepo.findByCategory(category).stream().map(this::postToDto).collect(Collectors.toList());
    }

    // ---------------- GET BY USER ----------------
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getPostByUser(Integer userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        return postRepo.findByUser(user).stream().map(this::postToDto).collect(Collectors.toList());
    }

    // ---------------- SEARCH ----------------
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> searchPostsByTitle(String keyword) {

        return postRepo.findByTitleContainingIgnoreCase(keyword).stream().map(this::postToDto).collect(Collectors.toList());
    }

    // ======================================================
    // DTO MAPPERS
    // ======================================================

    // SAFE: DTO → ENTITY
    private Post dtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    // SAFE + MANUAL: ENTITY → DTO
    private PostDto postToDto(Post post) {

        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImageName(post.getImageName());

        if (post.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(post.getCategory().getCategoryId());
            categoryDto.setCategoryTitle(post.getCategory().getCategoryTitle());
            categoryDto.setCategoryDescription(post.getCategory().getCategoryDescription());
            dto.setCategory(categoryDto);
        }

        return dto;
    }
}
