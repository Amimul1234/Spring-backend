package com.owo.OwoDokan.entity.admin_related.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.owo.OwoDokan.entity.admin_related.Shops;
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
public class Shop_keeper_orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_number;
    @Column(columnDefinition = "LONGTEXT")
    private String additional_comments;
    private double coupon_discount;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String delivery_address;
    @Column(nullable = false)
    private String method;
    private String receiver_phone;
    @Column(nullable = false)
    private String shop_phone;
    @Column(nullable = false)
    private String shipping_state;
    @Column(nullable = false)
    private String time_slot;
    @Column(nullable = false)
    private String order_time;
    @Column(nullable = false)
    private double total_amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH
            , CascadeType.REFRESH})
    @JsonBackReference
    private Shops shops;

    @OneToMany(
            targetEntity = Shop_keeper_ordered_products.class,
            mappedBy = "shop_keeper_orders",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Shop_keeper_ordered_products> shop_keeper_ordered_products = new ArrayList<>();
}
