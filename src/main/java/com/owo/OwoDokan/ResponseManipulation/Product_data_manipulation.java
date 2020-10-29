package com.owo.OwoDokan.ResponseManipulation;

import com.owo.OwoDokan.entity.Owo_product;

import java.util.ArrayList;
import java.util.List;

public class Product_data_manipulation {
    public boolean error;
    public List<Owo_product> products = new ArrayList<>();

    public Product_data_manipulation() {
    }

    public Product_data_manipulation(boolean error, List<Owo_product> products) {
        this.error = error;
        this.products = products;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Owo_product> getProducts() {
        return products;
    }

    public void setProducts(List<Owo_product> products) {
        this.products = products;
    }
}
