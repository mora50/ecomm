package com.stoom.ecomm.repositories;

import com.stoom.ecomm.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}