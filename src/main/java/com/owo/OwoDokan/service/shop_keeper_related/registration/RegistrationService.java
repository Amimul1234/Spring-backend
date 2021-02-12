package com.owo.OwoDokan.service.shop_keeper_related.registration;

import com.owo.OwoDokan.entity.admin_related.ShopKeeperPermissions;
import com.owo.OwoDokan.entity.admin_related.ShopPendingRequest;
import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.repository.shop_keeper_related.Debt.ShopRegistrationRequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final ShopRegistrationRequestRepo shopRegistrationRequestRepo;

    public RegistrationService(ShopRegistrationRequestRepo shopRegistrationRequestRepo) {
        this.shopRegistrationRequestRepo = shopRegistrationRequestRepo;
    }

    public ResponseEntity addNewRequest(ShopPendingRequest shopPendingRequest) {

        Shops shops = new Shops();

        shops.setLatitude(shopPendingRequest.getLatitude());
        shops.setLongitude(shopPendingRequest.getLongitude());
        shops.setApproved(false);
        shops.setBlocked(false);
        shops.setShop_address(shopPendingRequest.getShopAddress());
        shops.setShop_image_uri(shopPendingRequest.getShopImageUri());
        shops.setShop_keeper_nid_front_uri(shopPendingRequest.getShopKeeperNidFrontUri());
        shops.setShop_name(shopPendingRequest.getShopName());
        shops.setShop_owner_mobile(shopPendingRequest.getShopOwnerMobile());
        shops.setShop_owner_name(shopPendingRequest.getShopOwnerName());
        shops.setShop_service_mobile(shopPendingRequest.getShopServiceMobile());
        shops.setTrade_license_url(shopPendingRequest.getTradeLicenseUrl());

        shopPendingRequest.getCategoryPermissions().forEach(category -> {
            ShopKeeperPermissions shopKeeperPermissions = new ShopKeeperPermissions();
            shopKeeperPermissions.setShops(shops);
            shopKeeperPermissions.setPermittedCategory(category);
            shops.getShopKeeperPermissions().add(shopKeeperPermissions);
        });

        try
        {
            shopRegistrationRequestRepo.save(shops);
            return ResponseEntity.status(HttpStatus.OK).body("Requested for shop registration");
        }catch (Exception e)
        {
            logger.error("Error on Registration service, Error is: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can request for shop creation");
        }
    }
}
