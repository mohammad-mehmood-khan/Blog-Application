package org.mehmood.blogapplicationbackendproject.Repository;

import org.mehmood.blogapplicationbackendproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
