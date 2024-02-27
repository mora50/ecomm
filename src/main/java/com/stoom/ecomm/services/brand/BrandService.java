package com.stoom.ecomm.services.brand;

import com.stoom.ecomm.dto.request.CreateBrandRequest;
import com.stoom.ecomm.dto.request.PatchBrandRequest;
import com.stoom.ecomm.entities.Brand;
import com.stoom.ecomm.utils.PaginatedResponse;

public interface BrandService {
    Brand createBrand(CreateBrandRequest createBrandRequest);

    Brand updateBrand(PatchBrandRequest brandRequest, Long brandId);

    PaginatedResponse<Brand> findAllBrand(int page, int size);

    Brand findBrandById(Long id);
    
}