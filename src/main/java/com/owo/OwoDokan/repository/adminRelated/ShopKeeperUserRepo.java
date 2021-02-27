package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.registerAccount.ShopKeeperUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ShopKeeperUserRepo extends JpaRepository<ShopKeeperUser, Long> {
    @Query("SELECT e from ShopKeeperUser e WHERE e.mobileNumber=:mobile_number")
    Optional<ShopKeeperUser> findByMobileNumber(@Param("mobile_number") String mobile_number);

    @Query("SELECT e FROM ShopKeeperUser e WHERE e.accountEnabled = true")
    Optional<List<ShopKeeperUser>> getAllEnabledShops(Pageable pageable);

    @Query("SELECT e FROM ShopKeeperUser e WHERE e.accountEnabled = false")
    Optional<List<ShopKeeperUser>> getAllDisabledShopKeeper(Pageable pageable);
}
