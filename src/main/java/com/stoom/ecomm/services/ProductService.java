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
        log.debug("[start] ProductService - createProduct");

        Product product = productMapper.mapToEntity(createProductRequest);

        Product savedProduct = productRepository.save(product);

        log.debug("[finish] ProductService - createProduct");

        return savedProduct;
    }

    public Product updateProduct(UpdateProductRequest productRequest, Long productId) {
        log.debug("[start] ProductService - updateProduct");

        Product existingProduct = findProductById(productId);
        productMapper.updatePartial(existingProduct, productRequest);
        Product updatedProduct = productRepository.save(existingProduct);

        log.debug("[finish] ProductService - updateProduct");

        return updatedProduct;
    }

    public PaginatedResponse<Product> findAllProducts(int page, int size) {
        log.debug("[start] ProductService - findAllProducts");

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        log.debug("[finish] ProductService - findAllProducts");

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

    public Product findProductById(Long productId) {
        log.debug("[start] ProductService - findProductById");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        log.debug("[finish] ProductService - findProductById");

        return product;
    }
}