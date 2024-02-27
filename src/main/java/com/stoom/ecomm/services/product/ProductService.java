package com.stoom.ecomm.services.product;

import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Product;
import com.stoom.ecomm.utils.PaginatedResponse;

public interface ProductService {

    Product createProduct(CreateProductRequest createProductRequest);

    Product updateProduct(UpdateProductRequest productRequest, Long productId);

    PaginatedResponse<Product> findAllProducts(int page, int size);

    PaginatedResponse<Product> findProductByCategoryId(Long categoryId, int page, int size);

    PaginatedResponse<Product> findProductByBrandId(Long brandId, int page, int size);

    Product findProductById(Long productId);

    void deleteProductById(Long productId);
}