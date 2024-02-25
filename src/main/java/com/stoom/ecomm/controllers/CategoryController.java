package com.stoom.ecomm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.dto.response.CategoryResponse;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.services.CategoryService;
import com.stoom.ecomm.utils.PaginatedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        return categoryService.createCategory(createCategoryRequest);
    }

    @PatchMapping("/{categoryId}")
    public Category updateCategory(@RequestBody @Valid PatchCategoryRequest categoryRequest,
            @PathVariable Long categoryId) {
        return categoryService.updateCategory(categoryRequest, categoryId);
    }

    @GetMapping
    public PaginatedResponse<Category> findAllCategories(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return categoryService.findAllCategories(page, size);
    }

    @GetMapping("/{categoryId}")
    public Category findCategoryById(@PathVariable Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }
}