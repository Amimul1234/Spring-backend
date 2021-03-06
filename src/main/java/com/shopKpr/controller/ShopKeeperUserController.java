package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.registerAccount.ShopKeeperUser;
import com.shopKpr.entity.admin_related.registerAccount.UserShopKeeper;
import com.shopKpr.service.admin_related.ShopKeeperRegistrationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ShopKeeperUserController {

    private final ShopKeeperRegistrationService shopKeeperRegistrationService;

    public ShopKeeperUserController(ShopKeeperRegistrationService shopKeeperRegistrationService) {
        this.shopKeeperRegistrationService = shopKeeperRegistrationService;
    }

    @PostMapping("/registerShopKeeper")
    public String registerShopKeeper(@RequestBody UserShopKeeper userShopKeeper)
    {
        return shopKeeperRegistrationService.addNewShopKeeper(userShopKeeper);
    }

    @GetMapping("/getShopKeeper")
    public ShopKeeperUser getShopKeeper(@RequestParam(name = "mobileNumber") String mobileNumber)
    {
        return shopKeeperRegistrationService.getShopKeeper(mobileNumber);
    }

    @PutMapping("/enableShopKeeper")
    public String enableShopKeeper(@RequestParam("mobileNumber") String mobileNumber)
    {
        return shopKeeperRegistrationService.enableShopKeeper(mobileNumber);
    }

    @DeleteMapping("/deleteShopKeeper")
    public String deleteShopKeeper(@RequestParam("mobileNumber") String mobileNumber)
    {
        return shopKeeperRegistrationService.deleteShopKeeper(mobileNumber);
    }

    @PutMapping("/updateShopKeeperInfo")
    public String updateShopKeeperInfo(@RequestBody ShopKeeperUser shopKeeperUser)
    {
        return shopKeeperRegistrationService.updateShopInfo(shopKeeperUser);
    }

    @GetMapping("/getAllEnabledShopKeepers")
    public List<ShopKeeperUser> getAllEnabledShopKeepers(@RequestParam(name = "page") int page)
    {
        return shopKeeperRegistrationService.findAllEnabledShopKeeper(page);
    }

    @GetMapping("/getAllDisabledAccounts")
    public List<ShopKeeperUser> getAllDisabledAccounts(@RequestParam(name = "page") int page)
    {
        return shopKeeperRegistrationService.findAllDisabledShopKeeper(page);
    }

    @PutMapping("/disableShopKeeper")
    public String disableShopKeeper(@RequestParam("mobileNumber") String mobileNumber)
    {
        return shopKeeperRegistrationService.disableShopKeeper(mobileNumber);
    }
}
