package com.shopKpr.service.admin_related;

import com.shopKpr.entity.admin_related.registerAccount.ShopKeeperUser;
import com.shopKpr.entity.admin_related.registerAccount.UserShopKeeper;
import com.shopKpr.exceptions.NoEnabledShops;
import com.shopKpr.exceptions.ShopKeeperUserNotFount;
import com.shopKpr.repository.adminRelated.ShopKeeperUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShopKeeperRegistrationService {

    private final ShopKeeperUserRepo shopKeeperUserRepo;

    public ShopKeeperRegistrationService(ShopKeeperUserRepo shopKeeperUserRepo) {
        this.shopKeeperUserRepo = shopKeeperUserRepo;
    }


    public String addNewShopKeeper(UserShopKeeper userShopKeeper) {
        ShopKeeperUser shopKeeperUser = new ShopKeeperUser();

        shopKeeperUser.setImageUri(userShopKeeper.getImage());
        shopKeeperUser.setAccountEnabled(true);
        shopKeeperUser.setMobileNumber(userShopKeeper.getPhone());
        shopKeeperUser.setPin(userShopKeeper.getPin());
        shopKeeperUser.setName(userShopKeeper.getName());

        try
        {
            shopKeeperUserRepo.save(shopKeeperUser);
            return "Successfully Registered Shop User";
        }catch (Exception e)
        {
            log.error("Error occurred on ShopKeeperRegistrationService class, Error is: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ShopKeeperUser getShopKeeper(String mobile_number) {

        Optional<ShopKeeperUser> shopKeeperUserOptional = shopKeeperUserRepo.findByMobileNumber(mobile_number);

        if(shopKeeperUserOptional.isPresent())
        {
            return shopKeeperUserOptional.get();
        }
        else
        {
            throw new ShopKeeperUserNotFount(mobile_number);
        }
    }

    public List<ShopKeeperUser> findAllEnabledShopKeeper(int page) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        Optional<List<ShopKeeperUser>> optionalShopKeeperUsers = shopKeeperUserRepo.getAllEnabledShops(pageable);

        if(optionalShopKeeperUsers.isPresent())
        {
            List<ShopKeeperUser> shopKeeperUserList = optionalShopKeeperUsers.get();

            for(ShopKeeperUser shopKeeperUser : shopKeeperUserList) {
                shopKeeperUser.setPin(null);
            }
            return shopKeeperUserList;
        }
        else
        {
            throw new NoEnabledShops();
        }
    }

    public List<ShopKeeperUser> findAllDisabledShopKeeper(int page)
    {

        int pageSize = 10;

        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        Optional<List<ShopKeeperUser>> optionalShopKeeperUsers = shopKeeperUserRepo.getAllDisabledShopKeeper(pageable);

        if(optionalShopKeeperUsers.isPresent())
        {
            List<ShopKeeperUser> shopKeeperUserList = optionalShopKeeperUsers.get();

            for(ShopKeeperUser shopKeeperUser : shopKeeperUserList) {
                shopKeeperUser.setPin(null);
            }
            return shopKeeperUserList;
        }
        else
        {
            throw new NoEnabledShops();
        }
    }

    @Transactional
    public String disableShopKeeper(String mobileNumber) {
        Optional<ShopKeeperUser> shopKeeperUserOptional = shopKeeperUserRepo.findByMobileNumber(mobileNumber);

        if(shopKeeperUserOptional.isPresent())
        {
            ShopKeeperUser shopKeeperUser = shopKeeperUserOptional.get();
            shopKeeperUser.setAccountEnabled(false);

            try
            {
                shopKeeperUserRepo.save(shopKeeperUser);
                return "Shop Keeper Disabled Successfully";
            }catch (Exception e)
            {
                log.error("Error occurred, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new ShopKeeperUserNotFount(mobileNumber);
        }
    }

    @Transactional
    public String deleteShopKeeper(String mobileNumber) {

        Optional<ShopKeeperUser> shopKeeperUserOptional = shopKeeperUserRepo.findByMobileNumber(mobileNumber);

        if(shopKeeperUserOptional.isPresent())
        {
            ShopKeeperUser shopKeeperUser = shopKeeperUserOptional.get();
            shopKeeperUser.setAccountEnabled(false);

            try
            {
                shopKeeperUserRepo.delete(shopKeeperUser);
                return "Shop Keeper deleted Successfully";
            }catch (Exception e)
            {
                log.error("Error occurred, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new ShopKeeperUserNotFount(mobileNumber);
        }
    }

    @Transactional
    public String enableShopKeeper(String mobileNumber) {

        Optional<ShopKeeperUser> shopKeeperUserOptional = shopKeeperUserRepo.findByMobileNumber(mobileNumber);

        if(shopKeeperUserOptional.isPresent())
        {
            ShopKeeperUser shopKeeperUser = shopKeeperUserOptional.get();
            shopKeeperUser.setAccountEnabled(true);

            try
            {
                shopKeeperUserRepo.save(shopKeeperUser);
                return "Shop Keeper Enabled Successfully";
            }catch (Exception e)
            {
                log.error("Error occurred, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new ShopKeeperUserNotFount(mobileNumber);
        }
    }

    @Transactional
    public String updateShopInfo(ShopKeeperUser shopKeeperUser) {
        Optional<ShopKeeperUser> shopKeeperUserOptional = shopKeeperUserRepo.findById(shopKeeperUser.getShopKeeperId());

        if(shopKeeperUserOptional.isPresent())
        {
            ShopKeeperUser shopKeeperUser1 = shopKeeperUserOptional.get();

            shopKeeperUser1.setPin(shopKeeperUser.getPin());
            shopKeeperUser1.setImageUri(shopKeeperUser.getImageUri());
            shopKeeperUser1.setName(shopKeeperUser.getName());

            shopKeeperUserRepo.save(shopKeeperUser1);

            return "Shop Keeper Information Updated Successfully";
        }
        else
        {
            throw new ShopKeeperUserNotFount(shopKeeperUser.getMobileNumber());
        }
    }
}
