package com.owo.OwoDokan.repository;

import com.owo.OwoDokan.entity.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {
    @Query("SELECT e.brand_name FROM Brands e WHERE e.category = :category")
    List<String> getBrands(@Param("category") String category);

    @Query("SELECT e FROM Brands e WHERE e.category IN (:product_categories)")
    Page<Brands> findBrandViaCategories(@Param("product_categories") List<String> product_categories, Pageable pageable);
}