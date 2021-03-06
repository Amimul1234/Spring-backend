package com.shopKpr.repository.adminRelated;

import com.shopKpr.entity.admin_related.OffersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OffersEntity, Long> {

    @Query("SELECT t.offer_image FROM OffersEntity t WHERE t.categoryEntity.categoryId IN(:categoryIds) AND t.enabled = true")
    Optional<List<String>> findByCategoryId(@Param("categoryIds") List<Long> categoryIds);
}
