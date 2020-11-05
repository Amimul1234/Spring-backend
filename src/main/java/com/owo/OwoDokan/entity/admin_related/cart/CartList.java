package com.owo.OwoDokan.entity.admin_related.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CartList {
    @Id
    @Column(nullable = false)
    private String mobile_number;

    @OneToMany(
            targetEntity = Cart_list_product.class,
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cart_list_product> cart_list_products = new ArrayList<>();
}
