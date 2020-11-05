package com.owo.OwoDokan.controller;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.service.admin_related.Shop_keeper_cart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
