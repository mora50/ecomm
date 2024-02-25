package com.stoom.ecomm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.ecomm.dto.request.CreateBrandRequest;
import com.stoom.ecomm.dto.request.PatchBrandRequest;
import com.stoom.ecomm.entities.Brand;
import com.stoom.ecomm.services.BrandService;
import com.stoom.ecomm.utils.PaginatedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand createBrand(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
        return brandService.createBrand(createBrandRequest);
    }

    @PatchMapping("/{brandId}")
    public Brand updateBrand(@RequestBody @Valid PatchBrandRequest brandRequest, @PathVariable Long brandId) {
        return brandService.updateBrand(brandRequest, brandId);
    }

    @GetMapping
    public PaginatedResponse<Brand> findAllBrads(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return brandService.findAllBrand(page, size);
    }

    @GetMapping("/{brandId}")
    public Brand findBrandById(@PathVariable Long brandId) {
        return brandService.findBrandById(brandId);
    }
}