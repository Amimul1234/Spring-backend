package com.owo.OwoDokan.entity;

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
    int id;
    private double latitude;
    private double longitude;
    private String shop_address;
    private String shop_image_url;
    private String shop_keeper_nid_front;
    private String shop_name;
    private String shop_owner_mobile_number;
    private String shop_owner_name;
    private String shop_service_mobile;
    private String shop_trade_license_url;

    @OneToMany(mappedBy = "shops")
    private List<ShopKeeperProducts> shopKeeperProductsList = new ArrayList<>();
}
