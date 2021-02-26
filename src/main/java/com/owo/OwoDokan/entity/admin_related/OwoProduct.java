package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(indexes = { @Index(name = "productIndex", columnList = "productCategoryId, productSubCategoryId")})
public class OwoProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer productQuantity;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String productDescription;
    @Column(nullable = false)
    private String productCreationDate;
    @Column(nullable = false)
    private String productCreationTime;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String productImage;

    @ManyToOne
    @JsonBackReference
    private Brands brands;
}
