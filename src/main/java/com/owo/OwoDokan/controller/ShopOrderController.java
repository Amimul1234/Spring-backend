package com.owo.OwoDokan.controller;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.cart.Cart_list_product;
import com.owo.OwoDokan.service.admin_related.Shop_keeper_cart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopOrderController {
    private final Shop_keeper_cart shop_keeper_cart;

    public ShopOrderController(Shop_keeper_cart shop_keeper_cart) {
        this.shop_keeper_cart = shop_keeper_cart;
    }

    @PostMapping("/shop_keeper_cart")
    public void shop_keeper_cart(@RequestBody CartListFromClient cartListFromClient)
    {
        shop_keeper_cart.addCartItem(cartListFromClient);
    }

    @GetMapping("/shop_keeper_cart_products")
    public List<Cart_list_product> getCartListForShopKeeper(@RequestParam(name = "mobile_number") String mobile_number)
    {
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

}
