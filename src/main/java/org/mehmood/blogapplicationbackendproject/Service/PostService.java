package org.mehmood.blogapplicationbackendproject.Service;

import org.mehmood.blogapplicationbackendproject.payLoads.PostDto;
import org.mehmood.blogapplicationbackendproject.payLoads.PostResponseDto;


import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponseDto getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPostsByTitle(String keyword);
}
