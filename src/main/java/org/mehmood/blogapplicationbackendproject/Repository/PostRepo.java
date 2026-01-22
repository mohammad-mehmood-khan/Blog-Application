package org.mehmood.blogapplicationbackendproject.Repository;

import org.mehmood.blogapplicationbackendproject.entity.Category;
import org.mehmood.blogapplicationbackendproject.entity.Post;
import org.mehmood.blogapplicationbackendproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContainingIgnoreCase(String keyword);

}
