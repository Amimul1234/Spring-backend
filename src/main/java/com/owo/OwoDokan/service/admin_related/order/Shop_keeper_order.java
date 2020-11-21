package com.owo.OwoDokan.service.admin_related.order;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_ordered_products;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.repository.admin_related.ShopRepository;
import com.owo.OwoDokan.repository.admin_related.cart_repo.CartRepo;
import com.owo.OwoDokan.repository.admin_related.order_repo.Order_repo;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Shop_keeper_order {
    private final Order_repo order_repo;
    private final CartRepo cartRepo;
    private final ShopRepository shopRepository;

    public Shop_keeper_order(Order_repo order_repo, CartRepo cartRepo, ShopRepository shopRepository) {
        this.order_repo = order_repo;
        this.cartRepo = cartRepo;
        this.shopRepository = shopRepository;
    }


    public ResponseEntity addOrder(Shop_keeper_orders shop_keeper_order_param, String mobile_number) {

        Shops shops;

        try
        {
            shops = shopRepository.getByPhone(mobile_number);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }


        Shop_keeper_orders shopKeeperOrders = new Shop_keeper_orders();

        shopKeeperOrders.setAdditional_comments(shop_keeper_order_param.getAdditional_comments());
        shopKeeperOrders.setCoupon_discount(shop_keeper_order_param.getCoupon_discount());
        shopKeeperOrders.setDate(shop_keeper_order_param.getDate());
        shopKeeperOrders.setDelivery_address(shop_keeper_order_param.getDelivery_address());
        shopKeeperOrders.setMethod(shop_keeper_order_param.getMethod());
        shopKeeperOrders.setReceiver_phone(shop_keeper_order_param.getReceiver_phone());
        shopKeeperOrders.setShop_phone(shop_keeper_order_param.getShop_phone());
        shopKeeperOrders.setShipping_state(shop_keeper_order_param.getShipping_state());
        shopKeeperOrders.setTime_slot(shop_keeper_order_param.getTime_slot());
        shopKeeperOrders.setOrder_time(shop_keeper_order_param.getOrder_time());
        shopKeeperOrders.setTotal_amount(shop_keeper_order_param.getTotal_amount());

        shopKeeperOrders.setShops(shops);
        shops.getShop_keeper_orders().add(shopKeeperOrders);

        for(Shop_keeper_ordered_products shop_keeper_ordered_products : shop_keeper_order_param.getShop_keeper_ordered_products())
        {
            shop_keeper_ordered_products.setShop_keeper_orders(shopKeeperOrders);
            shopKeeperOrders.getShop_keeper_ordered_products().add(shop_keeper_ordered_products);
        }

        try
        {
            shopRepository.save(shops);
        }catch(Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }

        try
        {
            cartRepo.deleteById(shopKeeperOrders.getShop_phone());
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public Page<Shop_keeper_orders> getAllProducts(int page, String mobile_number) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return order_repo.findByMobileNumber(mobile_number, pageable);
    }
}
