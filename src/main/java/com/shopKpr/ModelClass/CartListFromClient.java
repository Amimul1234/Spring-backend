package com.shopKpr.ModelClass;

import com.shopKpr.entity.admin_related.cart.CartListProduct;
import java.io.Serializable;

public class CartListFromClient implements Serializable {
    private String mobile_number;
    private CartListProduct cartListProduct;

    public CartListFromClient(String mobile_number, CartListProduct cartListProduct) {
        this.mobile_number = mobile_number;
        this.cartListProduct = cartListProduct;
    }

    public CartListFromClient() {
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public CartListProduct getCartListProduct() {
        return cartListProduct;
    }

    public void setCartListProduct(CartListProduct cart_list_product) {
        this.cartListProduct = cart_list_product;
    }
}
