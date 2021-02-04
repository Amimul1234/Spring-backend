package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<Shops, Integer> {
    @Query("SELECT e FROM Shops e WHERE e.shop_service_mobile = :mobile_number")
    Shops getByPhone(@Param("mobile_number")String mobile_number);
}
