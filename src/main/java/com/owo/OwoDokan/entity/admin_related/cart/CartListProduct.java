package com.owo.OwoDokan.entity.admin_related.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CartListProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Long productCategoryId;
    @Column(nullable = false)
    private Long productSubCategoryId;
    @Column(nullable = false)
    private Double productPrice;
    @Column(nullable = false)
    private Double productDiscount;
    @Column(nullable = false)
    private int productQuantity;
    @Column(nullable = false)
    private String productAddingDate;
    @Column(nullable = false)
    private String productAddingTime;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String productImage;

    @ManyToOne
    @JsonBackReference
    private CartList cart;
}
