package com.stoom.ecomm.repositories;

import com.stoom.ecomm.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Page<Category> findByActiveTrue(Pageable page);
}