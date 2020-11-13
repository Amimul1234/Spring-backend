package com.owo.OwoDokan.controller.shopKeeper;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.cart.Cart_list_product;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.service.admin_related.cart.Shop_keeper_cart;
import com.owo.OwoDokan.service.admin_related.order.Shop_keeper_order;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ShopOrderController {
    private final Shop_keeper_cart shop_keeper_cart;
    private final Shop_keeper_order shop_keeper_order;

    public ShopOrderController(Shop_keeper_cart shop_keeper_cart, Shop_keeper_order shop_keeper_order) {
        this.shop_keeper_cart = shop_keeper_cart;
        this.shop_keeper_order = shop_keeper_order;
    }

    @PostMapping("/shop_keeper_cart")
    public void shop_keeper_cart(@RequestBody CartListFromClient cartListFromClient)
    {
        shop_keeper_cart.addCartItem(cartListFromClient);
    }

    @GetMapping("/shop_keeper_cart_products")
    public List<Cart_list_product> getCartListForShopKeeper(@RequestParam(name = "mobile_number") String mobile_number) throws Exception {
        return shop_keeper_cart.getCartItems(mobile_number);
    }

    @PutMapping("/update_cart_list")
    public Cart_list_product updateCartProduct(@RequestBody Cart_list_product cart_list_product, @RequestParam(name = "mobile_number") String mobile_number)
    {
        return shop_keeper_cart.updateCartItem(cart_list_product, mobile_number);
    }

    @DeleteMapping("/delete_cart_product")
    public void deleteCartProduct(@RequestParam(name = "product_id") int product_id, @RequestParam(name = "mobile_number") String mobile_number)
    {
        shop_keeper_cart.deleteCartProduct(product_id, mobile_number);
    }

    @PostMapping("/shop_keeper_order")
    public void add_shop_order(@RequestBody Shop_keeper_orders shop_keeper_orders)
    {
        shop_keeper_order.addOrder(shop_keeper_orders);
    }

    @GetMapping("/get_shop_keeper_order")
    public List<Shop_keeper_orders> getShopKeeperOrders(@RequestParam(name = "page") int page, @RequestParam(name = "mobile_number") String mobile_number)
    {
        Page<Shop_keeper_orders> pagedList = shop_keeper_order.getAllProducts(page, mobile_number);
        return pagedList.getContent();
    }
}
