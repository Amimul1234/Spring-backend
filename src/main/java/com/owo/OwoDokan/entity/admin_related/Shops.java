package com.owo.OwoDokan.entity.admin_related;

import com.owo.OwoDokan.entity.shopKeeper_related.ShopKeeperProducts;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "shops")
    private List<ShopKeeperProducts> shopKeeperProductsList = new ArrayList<>();
}
