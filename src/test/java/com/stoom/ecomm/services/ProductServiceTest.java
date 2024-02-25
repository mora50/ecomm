package com.stoom.ecomm.services;

import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Brand;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.entities.Product;
import com.stoom.ecomm.mappers.ProductMapper;
import com.stoom.ecomm.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful creation of a product when requested with valid data")
    public void createProduct() {

        CreateProductRequest request = createProductRequest();

        Product product = createProductEntity(1L);

        when(productMapper.mapToEntity(request)).thenReturn(product);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(request);

        assertNotNull(result);

        assertEquals(product.getName(), result.getName());

        assertEquals(product.getDescription(), result.getDescription());

        assertEquals(product.getPrice(), result.getPrice());

        assertEquals(product.getActive(), result.getActive());

        assertEquals(product.getBrand().getId(), result.getBrand().getId());

        assertEquals(product.getCategories().size(), result.getCategories().size());
    }


    @Test
    @DisplayName("Successful update of a product with valid data")
    public void updateProduct() {

        Long productId = 1L;

        UpdateProductRequest updateRequest = updateProductRequest();

        Product existingProduct = createProductEntity(1L);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        when(productMapper.updatePartial(updateRequest, existingProduct)).thenReturn(existingProduct);

        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(updateRequest, productId);

        assertEquals(existingProduct, result);
    }


    @Test
    @DisplayName("Paginated query of all products")
    public void testFindAllProducts() {

        List<Product> productList = createProductList();

        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findByActiveTrue(any(Pageable.class))).thenReturn(productPage);

        int page = 0;

        int size = 3;

        var result = productService.findAllProducts(page, size);

        assertNotNull(result);
        assertEquals(productList.size(), result.content().size());
        assertTrue(result.last());
        assertEquals(1, result.totalPages());
        assertEquals(productList.size(), result.totalElements());
        assertTrue(result.first());
        assertEquals(size, result.size());
        assertEquals(page, result.number());
        assertFalse(result.empty());
        assertEquals(productList.size(), result.numberOfElements());

    }


    @Test
    @DisplayName("Find products by category")
    public void testFindProductByCategoryId() {
        List<Product> productList = createProductList();
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findProductsByCategoryId(anyLong(), any(Pageable.class))).thenReturn(productPage);

        Long categoryId = 1L;
        int page = 0;
        int size = 10;
        var result = productService.findProductByCategoryId(categoryId, page, size);

        int sizeExpected = 3;

        // Assertions
        assertNotNull(result);
        assertEquals(productList.size(), result.content().size());
        assertTrue(result.last());
        assertEquals(1, result.totalPages());
        assertEquals(productList.size(), result.totalElements());
        assertTrue(result.first());
        assertEquals(sizeExpected, result.size());
        assertEquals(page, result.number());
        assertFalse(result.empty());
        assertEquals(productList.size(), result.numberOfElements());

    }


    @Test
    @DisplayName("Find products by brand id")
    public void testFindProductByBrandId() {
        List<Product> productList = createProductList();
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findProductsByBrandId(anyLong(), any(Pageable.class))).thenReturn(productPage);

        Long brandId = 1L;
        int page = 0;
        int size = 10;
        var result = productService.findProductByBrandId(brandId, page, size);

        int sizeExpected = 3;

        assertNotNull(result);
        assertEquals(productList.size(), result.content().size());
        assertTrue(result.last());
        assertEquals(1, result.totalPages());
        assertEquals(productList.size(), result.totalElements());
        assertTrue(result.first());
        assertEquals(sizeExpected, result.size());
        assertEquals(page, result.number());
        assertFalse(result.empty());
        assertEquals(productList.size(), result.numberOfElements());

    }


    @Test
    @DisplayName("Find product by id")
    public void findProductById() {

        var productId = 1L;

        var product = createProductEntity(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        var result = productService.findProductById(productId);

        assertEquals(result, product);
    }


    Product createProductEntity(Long id) {
        Product product = new Product();

        product.setName("Example Product");
        product.setImgUrl("https://example.com/image.jpg");
        product.setDescription("This is an example product description.");
        product.setPrice(99.99);
        product.setSku("ABC123");
        product.setActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Brand brand = new Brand();
        brand.setId(id);
        brand.setName("Example Brand");
        product.setBrand(brand);

        Set<Category> categories = new HashSet<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Example Category 1");
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Example Category 2");
        categories.add(category2);

        product.setCategories(categories);


        return product;
    }


    CreateProductRequest createProductRequest() {

        return new CreateProductRequest(
                "Example Product",
                99.99,
                true,
                "This is an example product description.",
                "ABC123",
                "https://example.com/image.jpg",
                1L,
                Set.of(1L, 2L)
        );
    }


    UpdateProductRequest updateProductRequest() {

        return new UpdateProductRequest(
                "Example Product 2",
                "This is an example product description.",
                99.99,
                "ABC123",
                "https://example.com/image.jpg",
                true,
                1L,
                Set.of(1L, 2L
                )
        );
    }

    List<Product> createProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(createProductEntity(1L));
        productList.add(createProductEntity(2L));
        productList.add(createProductEntity(3L));
        return productList;
    }
}