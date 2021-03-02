package com.owo.OwoDokan.service.admin_related.cart;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.cart.CartList;
import com.owo.OwoDokan.entity.admin_related.cart.CartListProduct;
import com.owo.OwoDokan.repository.adminRelated.cart_repo.CartRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class Shop_keeper_cart_service {

    private final CartRepo cartRepo;

    public Shop_keeper_cart_service(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Transactional
    public String addCartItem(CartListFromClient cartListFromClient) {

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

        CartListProduct cartListProduct;
        cartListProduct = cartListFromClient.getCartListProduct();
        cartListProduct.setCart(cartList);

        cartList.getCartListProducts().add(cartListProduct);

        try {
            cartRepo.save(cartList);
            return "Product Added to Cart";
        }catch (Exception e)
        {
            log.error("Error occurred while adding product to cart, Error is: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<CartListProduct> getCartItems(String mobile_number) {
        return cartRepo.getOne(mobile_number).getCartListProducts();
    }

    public CartListProduct updateCartItem(CartListProduct cart_list_product, String mobile_number) {
        Long id = cart_list_product.getProductId();

        CartList cartList = cartRepo.getOne(mobile_number);

        for(CartListProduct cartListProduct : cartList.getCartListProducts())
        {
            if(cartListProduct.getProductId().equals(id))
            {
                cartListProduct.setProductQuantity(cart_list_product.getProductQuantity());
                break;
            }
        }

        cartRepo.save(cartList);

        return cart_list_product;
    }

    public void deleteCartProduct(int id, String mobile_number) {

        CartList cartList = cartRepo.getOne(mobile_number);

        List<CartListProduct> cart_list_products;

        cart_list_products = cartList.getCartListProducts();

        for(CartListProduct cartListProduct : cart_list_products)
        {
            if(cartListProduct.getProductId() == id)
            {
                cart_list_products.remove(cartListProduct);
                break;
            }
        }

        cartList.setCartListProducts(cart_list_products);

        cartRepo.save(cartList);
    }
}
