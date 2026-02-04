package org.mehmood.blogapplicationbackendproject.Repository;

import org.mehmood.blogapplicationbackendproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
