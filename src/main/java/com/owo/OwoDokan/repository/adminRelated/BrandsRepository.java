package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {
    @Query("SELECT e FROM Brands e WHERE e.subCategoryEntity IN (:product_categories)")
    Page<Brands> findBrandViaCategories(@Param("product_categories") List<String> product_categories, Pageable pageable);
}