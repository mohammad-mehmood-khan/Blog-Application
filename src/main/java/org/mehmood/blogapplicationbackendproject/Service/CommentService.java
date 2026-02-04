package org.mehmood.blogapplicationbackendproject.Service;

import org.mehmood.blogapplicationbackendproject.payLoads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    void deleteComment(Integer commentId);
}
