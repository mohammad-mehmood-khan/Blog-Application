package org.mehmood.blogapplicationbackendproject.Service;

import org.mehmood.blogapplicationbackendproject.payLoads.CategoryDto;


import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    void deleteCategory(Integer categoryId);

    CategoryDto getCategoryById(Integer categoryId);

    List<CategoryDto> getAllCategories();

}
