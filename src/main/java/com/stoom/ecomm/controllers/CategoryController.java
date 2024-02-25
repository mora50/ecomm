package com.stoom.ecomm.controllers;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.services.CategoryService;
import com.stoom.ecomm.utils.PaginatedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
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