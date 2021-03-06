package com.shopKpr.repository.adminRelated.cart_repo;

import com.shopKpr.entity.admin_related.cart.CartList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartList, String>{
}
