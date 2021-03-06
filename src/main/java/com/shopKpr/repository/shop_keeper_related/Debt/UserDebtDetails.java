package com.shopKpr.repository.shop_keeper_related.Debt;

import com.shopKpr.entity.shopKeeper_related.User_debt_details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDebtDetails extends JpaRepository<User_debt_details, Long> {
}
