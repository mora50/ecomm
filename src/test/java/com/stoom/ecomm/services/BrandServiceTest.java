package com.stoom.ecomm.services;


import com.stoom.ecomm.dto.request.CreateBrandRequest;
import com.stoom.ecomm.dto.request.PatchBrandRequest;
import com.stoom.ecomm.entities.Brand;
import com.stoom.ecomm.exceptions.NotFoundException;
import com.stoom.ecomm.mappers.BrandMapper;
import com.stoom.ecomm.repositories.BrandRepository;
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

public class BrandServiceTest {


    @Mock
    BrandRepository brandRepository;

    @Mock
    BrandMapper brandMapper;

    @InjectMocks
    BrandService brandService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful creation of a brand when requested with valid data")
    public void createBrand() {

        Brand brandEntity = createBrandEntity(1L);

        CreateBrandRequest request = createBrandRequest();

        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);

        when(brandMapper.mapToEntity(request)).thenReturn(brandEntity);

        var response = brandService.createBrand(request);

        assertEquals(brandEntity, response);
    }


    @Test
    @DisplayName("Successful update of a brand with valid data")
    public void updateBrand() {
        PatchBrandRequest brandRequest = patchBrandRequest();
        Long brandId = 1L;
        Brand existBrand = createBrandEntity(1L);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(existBrand));

        when(brandMapper.updateParcial(existBrand, brandRequest)).thenReturn(existBrand);

        when(brandRepository.save(existBrand)).thenReturn(existBrand);

        Brand result = brandService.updateBrand(brandRequest, brandId);

        assertEquals(existBrand, result);

    }

    @Test
    @DisplayName("Throw NotFoundException when brand is not found by ID")
    public void testFindBrandById_NotFound() {
        Long brandId = 1L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> brandService.findBrandById(brandId));
    }


    @Test
    @DisplayName("Paginated query of all categories")
    public void testFindAllBrand() {
        int page = 0;
        int size = 10;
        List<Brand> brandList = createBrandList();
        Page<Brand> brandPage = new PageImpl<>(brandList);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandPage);

        PaginatedResponse<Brand> result = brandService.findAllBrand(page, size);


        assertNotNull(result);
        assertEquals(brandList, result.content());
        assertTrue(result.last());
        assertEquals(1, result.totalPages());
        assertEquals(brandList.size(), result.totalElements());
        assertTrue(result.first());
        assertEquals(brandList.size(), result.size());
        assertEquals(page, result.number());
        assertFalse(result.empty());
        assertEquals(brandList.size(), result.numberOfElements());
    }


    @Test
    @DisplayName("Search brand by id")
    public void findBrandById() {

        var brandId = 1L;

        var brandEntity = createBrandEntity(brandId);

        when(brandRepository.findById(brandId)).thenReturn(Optional.ofNullable(brandEntity));

        var result = brandService.findBrandById(brandId);

        assertEquals(result, brandEntity);
    }


    public Brand createBrandEntity(Long brandId) {
        Brand brand = new Brand();
        brand.setId(brandId);
        brand.setName("Nike");
        brand.setImgUrl("https://example.com/image.jpg");
        brand.setActive(true);
        return brand;
    }


    public CreateBrandRequest createBrandRequest() {

        return new CreateBrandRequest(
                "Example Brand",
                "https://example.com/image.jpg",
                true
        );
    }

    public PatchBrandRequest patchBrandRequest() {

        return new PatchBrandRequest(
                "Example Brand",
                "https://example.com/image.jpg",
                true
        );
    }

    List<Brand> createBrandList() {
        List<Brand> brandList = new ArrayList<>();
        brandList.add(createBrandEntity(1L));
        brandList.add(createBrandEntity(2L));
        brandList.add(createBrandEntity(3L));
        return brandList;
    }
}