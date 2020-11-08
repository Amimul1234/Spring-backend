package com.owo.OwoDokan.repository.admin_related.order_repo;

import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_repo extends JpaRepository<Shop_keeper_orders, Integer> {
}
