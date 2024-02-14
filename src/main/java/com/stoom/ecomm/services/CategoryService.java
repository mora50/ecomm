package com.stoom.ecomm.services;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.dto.response.CategoryResponse;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.exceptions.NotFoundException;
import com.stoom.ecomm.mappers.CategoryMapper;
import com.stoom.ecomm.repositories.CategoryRepository;
import com.stoom.ecomm.utils.PaginatedResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        log.debug("[start] CategoryService - createCategory");

        Category category = categoryMapper.mapToEntity(createCategoryRequest);

        Category savedCategory = categoryRepository.save(category);

        var response = categoryMapper.mapToResponse(savedCategory);

        log.debug("[finish] CategoryService - createCategory");

        return response;
    }

    public Category updateCategory(PatchCategoryRequest categoryRequest, Long categoryId) {
        log.debug("[start] CategoryService - updateCategory");

        Category existingCategory = findCategoryById(categoryId);
        categoryMapper.updatePartial(existingCategory, categoryRequest);
        Category updatedCategory = categoryRepository.save(existingCategory);

        log.debug("[finish] CategoryService - updateCategory");

        return updatedCategory;
    }

    public PaginatedResponse<Category> findAllCategories(int page, int size) {
        log.debug("[start] CategoryService - findAllCategories");

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        log.debug("[finish] CategoryService - findAllCategories");

        return new PaginatedResponse<Category>(
                categoryPage.getContent(),
                categoryPage.isLast(),
                categoryPage.getTotalPages(),
                categoryPage.getTotalElements(),
                categoryPage.isFirst(),
                categoryPage.getSize(),
                categoryPage.getNumber(),
                categoryPage.isEmpty(),
                categoryPage.getNumberOfElements()
        );
    }

    public Category findCategoryById(Long categoryId) {
        log.debug("[start] CategoryService - findCategoryById");

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found: " + categoryId));

        log.debug("[finish] CategoryService - findCategoryById");

        return category;
    }
}