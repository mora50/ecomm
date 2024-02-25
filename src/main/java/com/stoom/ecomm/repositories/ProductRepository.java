package com.stoom.ecomm.repositories;


import com.stoom.ecomm.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable page);

    @Query("""
                   SELECT p FROM Product p
                                 JOIN p.categories c
                                 WHERE c.id = :categoryId
                                     AND p.active = true
            """)
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable page);


    @Query("""
            SELECT p FROM Product p JOIN p.brand b
            WHERE b.id = :brandId
            """)
    Page<Product> findProductsByBrandId(@Param("brandId") Long brandId, Pageable page);
}