package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.entity.shopKeeper_related.ShopKeeperProducts;
import com.owo.OwoDokan.entity.shopKeeper_related.Shop_offers;
import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
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
public class Shops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shop_id;
    private double latitude;
    private double longitude;
    private String shop_address;
    private String shop_image_uri;
    private String shop_keeper_nid_front_uri;
    private String shop_name;
    private String shop_owner_mobile;
    private String shop_owner_name;
    private String shop_service_mobile;
    private String trade_license_url;

    @OneToMany(mappedBy = "shops",
            orphanRemoval = true)
    private List<ShopKeeperProducts> shopKeeperProductsList = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Shop_offers> shop_offersList = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Shop_keeper_orders> shop_keeper_orders = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<UserDebts> userDebts = new ArrayList<>();
}
