package com.stoom.ecomm.services;

import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.exceptions.NotFoundException;
import com.stoom.ecomm.mappers.CategoryMapper;
import com.stoom.ecomm.repositories.CategoryRepository;
import com.stoom.ecomm.utils.PaginatedResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful creation of a category when requested with valid data")
    public void createCategory() {

        Category categoryEntity = createCategoryEntity(1L);

        CreateCategoryRequest request = createCategoryRequest();

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        when(categoryMapper.mapToEntity(request)).thenReturn(categoryEntity);

        Category response = categoryService.createCategory(request);

        assertEquals(categoryEntity, response);
    }

    @Test
    @DisplayName("Successful update of a category with valid data")
    public void updateCategory() {

        PatchCategoryRequest categoryRequest = patchCategoryRequest();

        Long categoryId = 1L;

        Category existCategory = createCategoryEntity(1L);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existCategory));
        when(categoryMapper.updatePartial(existCategory, categoryRequest)).thenReturn(existCategory);
        when(categoryRepository.save(existCategory)).thenReturn(existCategory);

        Category result = categoryService.updateCategory(categoryRequest, categoryId);

        assertEquals(existCategory, result);
    }

    @Test
    @DisplayName("Throw NotFoundException when category is not found by ID")
    public void testFindCategoryById_NotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> categoryService.findCategoryById(categoryId));
    }

    @Test
    @DisplayName("Paginated query of all categories")
    public void testFindAllCategories() {
        int page = 0;
        int size = 10;
        List<Category> categoryList = createCategoryList();
        Page<Category> categoryPage = new PageImpl<>(categoryList);

        when(categoryRepository.findByActiveTrue(any(Pageable.class))).thenReturn(categoryPage);

        PaginatedResponse<Category> result = categoryService.findAllCategories(page, size);

        assertNotNull(result);
        assertEquals(categoryList, result.content());
        assertTrue(result.last());
        assertEquals(1, result.totalPages());
        assertEquals(categoryList.size(), result.totalElements());
        assertTrue(result.first());
        assertEquals(categoryList.size(), result.size());
        assertEquals(page, result.number());
        assertFalse(result.empty());
        assertEquals(categoryList.size(), result.numberOfElements());
    }

    @Test
    @DisplayName("Retrieve category by id")
    public void findCategoryById() {
        var categoryId = 1L;
        var categoryEntity = createCategoryEntity(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.ofNullable(categoryEntity));

        var result = categoryService.findCategoryById(categoryId);

        assertEquals(result, categoryEntity);
    }

    public Category createCategoryEntity(Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Example Category");
        category.setActive(true);
        category.setImgUrl("http://example.com");
        return category;
    }

    public CreateCategoryRequest createCategoryRequest() {
        return new CreateCategoryRequest(
                "Example Category",
                "http://example.com",
                true
        );
    }

    public PatchCategoryRequest patchCategoryRequest() {
        return new PatchCategoryRequest(
                1L,
                "Example Category",
                "http://example.com",
                true
        );
    }

    List<Category> createCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(createCategoryEntity(1L));
        categoryList.add(createCategoryEntity(2L));
        categoryList.add(createCategoryEntity(3L));
        return categoryList;
    }
}