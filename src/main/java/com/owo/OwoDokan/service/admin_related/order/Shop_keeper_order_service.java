package com.owo.OwoDokan.service.admin_related.order;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_ordered_products;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.repository.adminRelated.ShopRepository;
import com.owo.OwoDokan.repository.adminRelated.cart_repo.CartRepo;
import com.owo.OwoDokan.repository.adminRelated.order_repo.Order_repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Shop_keeper_order_service {
    private final Order_repo order_repo;
    private final CartRepo cartRepo;
    private final ShopRepository shopRepository;

    public Shop_keeper_order_service(Order_repo order_repo, CartRepo cartRepo, ShopRepository shopRepository) {
        this.order_repo = order_repo;
        this.cartRepo = cartRepo;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity addOrder(Shop_keeper_orders shop_keeper_order_param, String mobile_number) {

        Optional<Shops> shopsOptional = shopRepository.getByPhone(mobile_number);

        if(shopsOptional.isPresent())
        {
            Shops shops = shopsOptional.get();

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
            shops.getShopKeeperOrders().add(shopKeeperOrders);

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
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not find shop");
        }
    }

    public Page<Shop_keeper_orders> getAllProducts(int page, String mobile_number) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return order_repo.findByMobileNumber(mobile_number, pageable);
    }

    public ResponseEntity findAllByState(String pending) {
        try
        {
            return new ResponseEntity(order_repo.findAllByState(pending), HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity setOrderState(long order_id, String order_state) {
        Shop_keeper_orders shop_keeper_orders;

        try
        {
            shop_keeper_orders = order_repo.findOrderById(order_id);
            shop_keeper_orders.setShipping_state(order_state);
            order_repo.save(shop_keeper_orders);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public Page<Shop_keeper_orders> findCancelledOrders(int page_num) {
        int page_size = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page_num, page_size);
        return order_repo.findByCancelledOrders("Cancelled", pageable);
    }

    public Page<Shop_keeper_orders> findDeliveredOrders(int page_num) {
        int page_size = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page_num, page_size);
        return order_repo.findByCancelledOrders("Delivered", pageable);
    }
}
