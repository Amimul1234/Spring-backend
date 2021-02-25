package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = { @Index(name = "productIndex", columnList = "productCategoryId, productSubCategoryId") })
@Indexed
public class OwoProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    @FullTextField
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
