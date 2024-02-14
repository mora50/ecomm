package com.stoom.ecomm.controllers;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.dto.response.CategoryResponse;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.services.CategoryService;
import com.stoom.ecomm.utils.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        log.debug("[start] CategoryController - createCategory");
        var response = categoryService.createCategory(createCategoryRequest);
        log.debug("[finish] CategoryController - createCategory");
        return response;
    }

    @PatchMapping("/{categoryId}")
    public Category updateCategory(@RequestBody @Valid PatchCategoryRequest categoryRequest, @PathVariable Long categoryId) {
        log.debug("[start] CategoryController - updateCategory");
        var response = categoryService.updateCategory(categoryRequest, categoryId);
        log.debug("[finish] CategoryController - updateCategory");
        return response;
    }

    @GetMapping
    public PaginatedResponse<Category> findAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        log.debug("[start] CategoryController - findAllCategories");
        var response = categoryService.findAllCategories(page, size);
        log.debug("[finish] CategoryController - findAllCategories");
        return response;
    }

    @GetMapping("/{categoryId}")
    public Category findCategoryById(@PathVariable Long categoryId) {
        log.debug("[start] CategoryController - findCategoryById");
        var response = categoryService.findCategoryById(categoryId);
        log.debug("[finish] CategoryController - findCategoryById");
        return response;
    }
}