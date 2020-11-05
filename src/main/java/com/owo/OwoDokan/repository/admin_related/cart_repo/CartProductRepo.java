package com.owo.OwoDokan.repository.admin_related.cart_repo;

import com.owo.OwoDokan.entity.admin_related.cart.Cart_list_product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepo extends JpaRepository<Cart_list_product, Integer>{
}
