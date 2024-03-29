package com.stoom.ecomm.services.category;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category createCategory(CreateCategoryRequest createCategoryRequest) {
        log.info("[start] BrandService - creating category {}:", createCategoryRequest);

        Category category = categoryMapper.mapToEntity(createCategoryRequest);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(PatchCategoryRequest categoryRequest, Long categoryId) {
        log.info("[start] BrandService - updating category {}:", categoryRequest);

        Category existingCategory = findCategoryById(categoryId);
        categoryMapper.updatePartial(existingCategory, categoryRequest);
        Category updatedCategory = categoryRepository.save(existingCategory);

        log.debug("[finish] CategoryService - updateCategory");

        return updatedCategory;
    }

    @Override
    public PaginatedResponse<Category> findAllCategories(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage = categoryRepository.findByActiveTrue(pageable);

        return new PaginatedResponse<>(
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

    @Override
    public Category findCategoryById(Long categoryId) {

        log.info("[start] CategoryService - findCategoryById {}:", categoryId);


        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found: " + categoryId));
    }
}