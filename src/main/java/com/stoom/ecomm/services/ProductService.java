package com.stoom.ecomm.services;

import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Product;
import com.stoom.ecomm.exceptions.NotFoundException;
import com.stoom.ecomm.mappers.ProductMapper;
import com.stoom.ecomm.repositories.ProductRepository;
import com.stoom.ecomm.utils.PaginatedResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product createProduct(CreateProductRequest createProductRequest) {

        log.info("[start] ProductService - creating product {}: ", createProductRequest);

        Product product = productMapper.mapToEntity(createProductRequest);

        return productRepository.save(product);
    }

    public Product updateProduct(UpdateProductRequest productRequest, Long productId) {

        log.info("[start] ProductService - update product with Id {}: ", productId);

        log.info("[start] ProductService - update product with data {}: ", productRequest);

        Product existingProduct = findProductById(productId);

        productMapper.updatePartial(productRequest, existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);

        log.debug("[finish] ProductService - updateProduct");

        return updatedProduct;
    }

    public PaginatedResponse<Product> findAllProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByActiveTrue(pageable);

        return new PaginatedResponse<>(
                productPage.getContent(),
                productPage.isLast(),
                productPage.getTotalPages(),
                productPage.getTotalElements(),
                productPage.isFirst(),
                productPage.getSize(),
                productPage.getNumber(),
                productPage.isEmpty(),
                productPage.getNumberOfElements()
        );
    }


    public PaginatedResponse<Product> findProductByCategoryId(Long categoryId, int page, int size) {

        var pageable = PageRequest.of(page, size);

        var response = productRepository.findProductsByCategoryId(categoryId, pageable);

        if (response == null) {
            throw new NotFoundException("Product not found by category: " + categoryId);
        }

        return new PaginatedResponse<>(response.getContent(),
                response.isLast(),
                response.getTotalPages(),
                response.getTotalElements(),
                response.isFirst(),
                response.getSize(),
                response.getNumber(),
                response.isEmpty(),
                response.getNumberOfElements());

    }

    public PaginatedResponse<Product> findProductByBrandId(Long brandId, int page, int size) {

        var pageable = PageRequest.of(page, size);

        var response = productRepository.findProductsByBrandId(brandId, pageable);

        if (response == null) {
            throw new NotFoundException("Product not found by brandId: " + brandId);
        }

        return new PaginatedResponse<>(response.getContent(),
                response.isLast(),
                response.getTotalPages(),
                response.getTotalElements(),
                response.isFirst(),
                response.getSize(),
                response.getNumber(),
                response.isEmpty(),
                response.getNumberOfElements());

    }

    public Product findProductById(Long productId) {

        log.info("[start] ProductService - finding the product with Id {}: ", productId);

        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
    }
}