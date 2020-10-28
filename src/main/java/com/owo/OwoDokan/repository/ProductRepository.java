package com.owo.OwoDokan.repository;

import com.owo.OwoDokan.entity.Owo_product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Owo_product, Integer> {
    @Query("SELECT e FROM Owo_product e WHERE e.product_category IN (:category) order by e.product_price")
    Page<Owo_product> findByCategories(@Param("category") List<String> category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_category IN (:category) order by e.product_price DESC")
    Page<Owo_product> findByCategoriesDesc(@Param("category") List<String> category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_category = (:category) order by e.product_price")
    Page<Owo_product> findByproduct_category(@Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_category = (:category) order by e.product_price DESC")
    Page<Owo_product> findByproduct_categoryDesc(@Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_sub_category = (:sub_category) order by e.product_price")
    Page<Owo_product> findByproduct_sub_category(@Param("sub_category") String sub_category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_sub_category = (:sub_category) order by e.product_price DESC")
    Page<Owo_product> findByproduct_sub_categoryDesc(@Param("sub_category") String sub_category, Pageable pageable);

    @Query("SELECT e FROM Owo_product e WHERE e.product_id = (:id)")
    Owo_product findByProduct_Id(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Owo_product e WHERE e.product_id = (:product_id)")
    void delete(@Param("product_id") int product_id);

    @Query("SELECT e FROM Owo_product e WHERE e.product_category IN(:categories) and e.product_brand = (:product_brand) order by e.product_price")
    Page<Owo_product> findProductByBrand(Pageable pageable, @Param("product_brand") String product_brand, @Param("categories") List<String> categories);

    @Query("SELECT e FROM Owo_product e WHERE e.product_category IN(:categories) and e.product_brand = (:product_brand) order by e.product_price DESC")
    Page<Owo_product> findProductByBrandDesc(Pageable pageable, @Param("product_brand") String product_brand, @Param("categories") List<String> categories);

}
