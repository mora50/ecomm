package com.stoom.ecomm.services;


import com.stoom.ecomm.dto.request.CreateBrandRequest;
import com.stoom.ecomm.dto.request.PatchBrandRequest;
import com.stoom.ecomm.entities.Brand;
import com.stoom.ecomm.exceptions.NotFoundException;
import com.stoom.ecomm.mappers.BrandMapper;
import com.stoom.ecomm.repositories.BrandRepository;
import com.stoom.ecomm.utils.PaginatedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;

        this.brandMapper = brandMapper;
    }

    public Brand createBrand(CreateBrandRequest createBrandRequest) {

        log.info("[start] BrandService - creating brand {}:", createBrandRequest);

        var brand = brandMapper.mapToEntity(createBrandRequest);

        return brandRepository.save(brand);
    }

    public Brand updateBrand(PatchBrandRequest brandRequest, Long brandId) {

        log.info("[start] BrandService - update brand with Id {}: ", brandId);
        log.info("[start] BrandService - update brand with data {}: ", brandRequest);

        var existBrand = findBrandById(brandId);

        brandMapper.updateParcial(existBrand, brandRequest);

        return brandRepository.save(existBrand);
    }

    public PaginatedResponse<Brand> findAllBrand(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        var response = brandRepository.findAll(pageable);

        return new PaginatedResponse<>(
                response.getContent(),
                response.isLast(),
                response.getTotalPages(),
                response.getTotalElements(),
                response.isFirst(),
                response.getSize(),
                response.getNumber(),
                response.isEmpty(),
                response.getNumberOfElements()
        );
    }

    public Brand findBrandById(Long id) {

        log.info("[start] BrandService - finding brand by id: {} ", id);

        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Brand not found: " + id));
    }
}