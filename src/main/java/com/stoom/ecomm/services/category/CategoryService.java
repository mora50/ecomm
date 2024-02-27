package com.stoom.ecomm.services.category;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.utils.PaginatedResponse;

public interface CategoryService {

    Category createCategory(CreateCategoryRequest createBrandRequest);

    Category updateCategory(PatchCategoryRequest brandRequest, Long brandId);

    PaginatedResponse<Category> findAllCategories(int page, int size);

    Category findCategoryById(Long id);
    
}