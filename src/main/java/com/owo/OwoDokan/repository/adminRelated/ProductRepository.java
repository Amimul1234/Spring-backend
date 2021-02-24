package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.OwoProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<OwoProduct, Integer> {
    /*
    @Query("SELECT e FROM OwoProduct e WHERE e.product_category IN (:categories) order by e.product_price")
    Page<OwoProduct> findByCategories(@Param("categories") List<String> categories, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_category IN (:categories) order by e.product_price DESC")
    Page<OwoProduct> findByCategoriesDesc(@Param("categories") List<String> categories, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_category = :category order by e.product_price")
    Page<OwoProduct> findByproduct_category(@Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_category = :category order by e.product_price DESC")
    Page<OwoProduct> findByproduct_categoryDesc(@Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_sub_category = :sub_category order by e.product_price")
    Page<OwoProduct> findByproduct_sub_category(@Param("sub_category") String sub_category, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_sub_category = :sub_category order by e.product_price DESC")
    Page<OwoProduct> findByproduct_sub_categoryDesc(@Param("sub_category") String sub_category, Pageable pageable);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_id = :id")
    OwoProduct findByProduct_Id(@Param("id") long id);

    @Modifying
    @Query("DELETE FROM OwoProduct e WHERE e.product_id = :product_id")
    void delete(@Param("product_id") int product_id);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_category IN(:categories) and e.product_brand = :product_brand order by e.product_price")
    Page<OwoProduct> findProductByBrand(Pageable pageable, @Param("product_brand") String product_brand, @Param("categories") List<String> categories);

    @Query("SELECT e FROM OwoProduct e WHERE e.product_category IN(:categories) and e.product_brand = (:product_brand) order by e.product_price DESC")
    Page<OwoProduct> findProductByBrandDesc(Pageable pageable, @Param("product_brand") String product_brand, @Param("categories") List<String> categories);


     */
}

