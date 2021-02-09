package com.owo.OwoDokan.repository.adminRelated;

import com.owo.OwoDokan.entity.admin_related.ShopKeeperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ShopKeeperUserRepo extends JpaRepository<ShopKeeperUser, Long> {
    @Query("SELECT e from ShopKeeperUser e WHERE e.mobileNumber=:mobile_number")
    Optional<ShopKeeperUser> findByMobileNumber(@Param("mobile_number") String mobile_number);
}
