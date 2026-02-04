package org.mehmood.blogapplicationbackendproject.impl;

import org.mehmood.blogapplicationbackendproject.Repository.CommentRepo;
import org.mehmood.blogapplicationbackendproject.Repository.PostRepo;
import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.mehmood.blogapplicationbackendproject.Service.CommentService;
import org.mehmood.blogapplicationbackendproject.entity.Comment;
import org.mehmood.blogapplicationbackendproject.entity.Post;
import org.mehmood.blogapplicationbackendproject.entity.User;
import org.mehmood.blogapplicationbackendproject.exceptions.ResourceNotFoundException;
import org.mehmood.blogapplicationbackendproject.payLoads.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public CommentServiceImpl(CommentRepo commentRepo, ModelMapper modelMapper, PostRepo postRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        commentRepo.delete(comment);
    }
}

