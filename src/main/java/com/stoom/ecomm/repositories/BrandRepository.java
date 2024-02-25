package com.stoom.ecomm.repositories;

import com.stoom.ecomm.entities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> findByActiveTrue(Pageable page);
}