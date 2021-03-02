package com.owo.OwoDokan.repository.adminRelated.cart_repo;

import com.owo.OwoDokan.entity.admin_related.cart.CartListProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepo extends JpaRepository<CartListProduct, Long>{
}
