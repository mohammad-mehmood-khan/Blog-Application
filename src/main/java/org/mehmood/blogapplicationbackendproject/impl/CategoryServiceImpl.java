package org.mehmood.blogapplicationbackendproject.impl;

import org.mehmood.blogapplicationbackendproject.Repository.CategoryRepo;
import org.mehmood.blogapplicationbackendproject.Service.CategoryService;
import org.mehmood.blogapplicationbackendproject.entity.Category;
import org.mehmood.blogapplicationbackendproject.exceptions.ResourceNotFoundException;
import org.mehmood.blogapplicationbackendproject.payLoads.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.CategoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updateCat = this.categoryRepo.save(category);
        return this.CategoryToDto(updateCat);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException(" category not found "));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException(" category not found "));
        return this.CategoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = this.categoryRepo.findAll();
        return allCategories.stream().map(this::CategoryToDto).collect(Collectors.toList());
    }


    public Category dtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto CategoryToDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
