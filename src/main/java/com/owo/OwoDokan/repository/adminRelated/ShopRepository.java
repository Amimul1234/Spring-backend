package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.Shops;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shops, Long> {
    @Query("SELECT e FROM Shops e WHERE e.shop_owner_mobile = :mobile_number")
    Optional<Shops> getByPhone(@Param("mobile_number")String mobile_number);

    @Query("select e from Shops e where e.approved = false")
    Optional<List<Shops>> getAllShopRegistrationRequests(Pageable pageNumber);

    @Query("select e from Shops e where e.approved = true and e.blocked=false")
    Optional<List<Shops>> getAllRegisteredShops(Pageable pageNumber);
}
