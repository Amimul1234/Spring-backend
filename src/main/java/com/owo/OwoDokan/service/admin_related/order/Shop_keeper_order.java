package com.owo.OwoDokan.service.admin_related.order;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_ordered_products;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.repository.admin_related.cart_repo.CartRepo;
import com.owo.OwoDokan.repository.admin_related.order_repo.Order_repo;
import org.springframework.stereotype.Service;

@Service
public class Shop_keeper_order {

    private final Order_repo order_repo;
    private final CartRepo cartRepo;

    public Shop_keeper_order(Order_repo order_repo, CartRepo cartRepo) {
        this.order_repo = order_repo;
        this.cartRepo = cartRepo;
    }


    public void addOrder(Shop_keeper_orders shop_keeper_order) {
        Shop_keeper_orders shopKeeperOrders = new Shop_keeper_orders();

        shopKeeperOrders.setAdditional_comments(shop_keeper_order.getAdditional_comments());
        shopKeeperOrders.setCoupon_discount(shop_keeper_order.getCoupon_discount());
        shopKeeperOrders.setDate(shop_keeper_order.getDate());
        shopKeeperOrders.setDelivery_address(shop_keeper_order.getDelivery_address());
        shopKeeperOrders.setMethod(shop_keeper_order.getMethod());
        shopKeeperOrders.setReceiver_phone(shop_keeper_order.getReceiver_phone());
        shopKeeperOrders.setShop_phone(shop_keeper_order.getShop_phone());
        shopKeeperOrders.setShipping_state(shop_keeper_order.getShipping_state());
        shopKeeperOrders.setTime_slot(shop_keeper_order.getTime_slot());
        shopKeeperOrders.setOrder_time(shop_keeper_order.getOrder_time());
        shopKeeperOrders.setTotal_amount(shop_keeper_order.getTotal_amount());

        for(Shop_keeper_ordered_products shop_keeper_ordered_products : shop_keeper_order.getShop_keeper_ordered_products())
        {
            shop_keeper_ordered_products.setShop_keeper_orders(shopKeeperOrders);
            shopKeeperOrders.getShop_keeper_ordered_products().add(shop_keeper_ordered_products);
        }

        try
        {
            order_repo.save(shopKeeperOrders);
            cartRepo.deleteById(shopKeeperOrders.getShop_phone());
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
