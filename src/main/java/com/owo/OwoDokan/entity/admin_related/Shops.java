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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "shop_id", name = "shop_id"),
        @Index(columnList = "shop_owner_mobile", name = "shop_owner_mobile")})
public class Shops implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shop_id;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    private Boolean approved;
    @Column(nullable = false)
    private String shop_address;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String shop_image_uri;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String shop_keeper_nid_front_uri;
    @Column(nullable = false)
    private String shop_name;
    @Column(nullable = false)
    private String shop_owner_mobile;
    @Column(nullable = false)
    private String shop_owner_name;
    @Column(nullable = false)
    private String shop_service_mobile;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String trade_license_url;

    @OneToMany(mappedBy = "shops",
            orphanRemoval = true)
    private List<ShopKeeperProducts> shopKeeperProductsList = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true, fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Shop_offers> shopOffersList = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Shop_keeper_orders> shopKeeperOrders = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true, fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<UserDebts> userDebts = new ArrayList<>();

    @OneToMany(
            mappedBy = "shops",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true, fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<ShopKeeperPermissions> shopKeeperPermissions = new ArrayList<>();
}
