package com.shopKpr.entity.shopKeeper_related;

import com.shopKpr.entity.admin_related.Shops;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ShopKeeperProducts{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long product_id;
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
    @Column(nullable = false)
    private String product_description;
    @Column(nullable = false)
    private String product_creation_date;
    @Column(nullable = false)
    private String product_creation_time;
    @Column(nullable = false)
    private String product_sub_category;
    @Column(nullable = false)
    private String product_brand;

    @ManyToOne
    private Shops shops;
}
