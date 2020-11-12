package com.owo.OwoDokan.repository.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDebt extends JpaRepository<UserDebts, Long> {
    @Query("SELECT e from UserDebts e WHERE e.user_mobile_number = :mobile_number")
    UserDebts findByUserMobileNumber(@Param("mobile_number") String mobile_number);
}
