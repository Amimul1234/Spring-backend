package com.owo.OwoDokan.service.admin_related.cart;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.cart.CartList;
import com.owo.OwoDokan.entity.admin_related.cart.Cart_list_product;
import com.owo.OwoDokan.repository.admin_related.cart_repo.CartRepo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class Shop_keeper_cart {

    private final CartRepo cartRepo;

    public Shop_keeper_cart(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

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

    public List<Cart_list_product> getCartItems(String mobile_number) throws Exception{
        return cartRepo.getOne(mobile_number).getCart_list_products();
    }

    public Cart_list_product updateCartItem(Cart_list_product cart_list_product, String mobile_number) {
        long id = cart_list_product.getProduct_id();
        CartList cartList = cartRepo.getOne(mobile_number);

        for(Cart_list_product cartListProduct : cartList.getCart_list_products())
        {
            if(cartListProduct.getProduct_id() == id)
            {
                cartListProduct.setProduct_quantity(cart_list_product.getProduct_quantity());
                break;
            }
        }

        cartRepo.save(cartList);

        return cart_list_product;
    }

    public void deleteCartProduct(int id, String mobile_number) {

        CartList cartList = cartRepo.getOne(mobile_number);

        List<Cart_list_product> cart_list_products = new ArrayList<>();

        cart_list_products = cartList.getCart_list_products();

        for(Cart_list_product cartListProduct : cart_list_products)
        {
            if(cartListProduct.getProduct_id() == id)
            {
                cart_list_products.remove(cartListProduct);
                break;
            }
        }

        cartList.setCart_list_products(cart_list_products);

        cartRepo.save(cartList);
    }
}
