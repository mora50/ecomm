package com.stoom.ecomm.controllers;


import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Product;
import com.stoom.ecomm.services.ProductService;
import com.stoom.ecomm.utils.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Log4j2
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        log.debug("[start] ProductController - createProduct");
        var response = productService.createProduct(createProductRequest);
        log.debug("[finish] ProductController - createProduct");
        return response;
    }

    @PatchMapping("/{productId}")
    public Product updateProduct(@RequestBody @Valid UpdateProductRequest productRequest, @PathVariable Long productId) {
        log.debug("[start] ProductController - updateProduct");
        var response = productService.updateProduct(productRequest, productId);
        log.debug("[finish] ProductController - updateProduct");
        return response;
    }

    @GetMapping
    public PaginatedResponse<Product> findAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        log.debug("[start] ProductController - findAllProducts");
        var response = productService.findAllProducts(page, size);
        log.debug("[finish] ProductController - findAllProducts");
        return response;
    }

    @GetMapping("/{productId}")
    public Product findProductById(@PathVariable Long productId) {
        log.debug("[start] ProductController - findProductById");
        var response = productService.findProductById(productId);
        log.debug("[finish] ProductController - findProductById");
        return response;
    }
}