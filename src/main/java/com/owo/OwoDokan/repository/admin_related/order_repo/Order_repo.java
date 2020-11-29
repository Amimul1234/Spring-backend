package com.owo.OwoDokan.repository.admin_related.order_repo;

import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Order_repo extends JpaRepository<Shop_keeper_orders, Integer> {
    @Query("SELECT e FROM Shop_keeper_orders e WHERE e.shop_phone = :mobile_number")
    Page<Shop_keeper_orders> findByMobileNumber(@Param("mobile_number") String mobile_number, Pageable pageable);

    @Query("SELECT e FROM Shop_keeper_orders e WHERE e.shipping_state = :state")
    List<Shop_keeper_orders> findAllByState(@Param("state") String state);

    @Query("SELECT e FROM Shop_keeper_orders e WHERE e.order_number = :order_number")
    Shop_keeper_orders findOrderById(@Param("order_number") long order_number);

    @Query("SELECT e FROM Shop_keeper_orders e WHERE e.shipping_state = :state")
    Page<Shop_keeper_orders> findByCancelledOrders(@Param("state") String cancelled, Pageable pageable);
}
