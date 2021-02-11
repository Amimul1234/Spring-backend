package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.ShopKeeperUser;
import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.admin_related.UserShopKeeper;
import com.owo.OwoDokan.repository.adminRelated.ShopKeeperUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopKeeperRegistrationService {
    private final ShopKeeperUserRepo shopKeeperUserRepo;
    private final Logger logger = LoggerFactory.getLogger(ShopKeeperRegistrationService.class);

    public ShopKeeperRegistrationService(ShopKeeperUserRepo shopKeeperUserRepo) {
        this.shopKeeperUserRepo = shopKeeperUserRepo;
    }


    public ResponseEntity addNewShopKeeper(UserShopKeeper userShopKeeper) {
        ShopKeeperUser shopKeeperUser = new ShopKeeperUser();

        shopKeeperUser.setImageUri(userShopKeeper.getImage());
        shopKeeperUser.setAccountEnabled(true);
        shopKeeperUser.setMobileNumber(userShopKeeper.getPhone());
        shopKeeperUser.setPin(userShopKeeper.getPin());
        shopKeeperUser.setName(userShopKeeper.getName());

        try
        {
            shopKeeperUserRepo.save(shopKeeperUser);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Registered Shop User");
        }catch (Exception e)
        {
            logger.error("Error occurred on ShopKeeperRegistrationService class, Error is: "+e.getMessage());
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getShopKeeper(String mobile_number) {

        Optional<ShopKeeperUser> shopKeeperUser = shopKeeperUserRepo.findByMobileNumber(mobile_number);

        return shopKeeperUser.map(keeperUser -> ResponseEntity.status(HttpStatus.OK).body(keeperUser)).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
