package com.owo.OwoDokan.entity.admin_related;

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
public class Owo_product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_id;
    @Column(nullable = false)
    private String product_name;
    @Column(nullable = false)
    private String product_category;
    @Column(nullable = false)
    private double product_price;
    @Column(nullable = false)
    private double product_discount;
    @Column(nullable = false)
    private int product_quantity;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String product_description;
    @Column(nullable = false)
    private String product_creation_date;
    @Column(nullable = false)
    private String product_creation_time;
    @Column(nullable = false)
    private String product_sub_category;
    @Column(nullable = false)
    private String product_brand;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String product_image;
}
