package com.stoom.ecomm.controllers;

import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Product;
import com.stoom.ecomm.services.product.ProductService;
import com.stoom.ecomm.utils.PaginatedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @PatchMapping("/{productId}")
    public Product updateProduct(@RequestBody @Valid UpdateProductRequest productRequest,
                                 @PathVariable Long productId) {
        return productService.updateProduct(productRequest, productId);
    }

    @GetMapping
    public PaginatedResponse<Product> findAllProducts(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "2") int size) {
        return productService.findAllProducts(page, size);
    }

    @GetMapping("/{productId}")
    public Product findProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    @GetMapping("/category/{categoryId}")
    public PaginatedResponse<Product> findProductByCategoryId(@PathVariable Long categoryId,
                                                              @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return productService.findProductByCategoryId(categoryId, page, size);
    }

    @GetMapping("/brand/{brandId}")
    public PaginatedResponse<Product> findProductByBrandId(@PathVariable Long brandId,
                                                           @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return productService.findProductByBrandId(brandId, page, size);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}