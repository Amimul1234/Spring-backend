package com.owo.OwoDokan.repository.admin_related.order_repo;

import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_ordered_products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ordered_products_repo extends JpaRepository<Shop_keeper_ordered_products, Integer> {
}
