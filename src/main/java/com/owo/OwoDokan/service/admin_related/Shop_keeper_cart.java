package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.cart.CartList;
import com.owo.OwoDokan.entity.admin_related.cart.Cart_list_product;
import com.owo.OwoDokan.repository.admin_related.cart_repo.CartRepo;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class Shop_keeper_cart {
    private final CartRepo cartRepo;

    public Shop_keeper_cart(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Transactional
    public CartList addCartItem(CartListFromClient cartListFromClient) {

        CartList cartList;

        if(cartRepo.existsById(cartListFromClient.getMobile_number()))
        {
            cartList = cartRepo.getOne(cartListFromClient.getMobile_number());
        }
        else
        {
            cartList = new CartList();
        }

        cartList.setMobile_number(cartListFromClient.getMobile_number());

        Cart_list_product cart_list_product;
        cart_list_product = cartListFromClient.getCart_list_product();
        cart_list_product.setCart(cartList);

        cartList.getCart_list_products().add(cart_list_product);

        return cartRepo.save(cartList);
    }
}
