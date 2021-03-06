package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.Shops;
import com.shopKpr.service.admin_related.ShopAddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {
    private final ShopAddingService shopAddingService;

    public ShopController(ShopAddingService shopAddingService) {
        this.shopAddingService = shopAddingService;
    }

    @PostMapping("/approveShop")
    public Shops approveShop(@RequestBody Shops shops)
    {
        return shopAddingService.approveNewShop(shops);
    }

    @PostMapping("/updateShopInfo")
    public Shops updateShop(@RequestBody Shops  shops)
    {
        return shopAddingService.updateShop(shops);
    }

    @GetMapping("/getShopInfo")
    public Shops get_shop_info(@RequestParam(name = "shop_phone") String shop_phone) {
        return shopAddingService.getShopInfo(shop_phone);
    }

    @GetMapping("/getAllShopRegistrationRequests")
    public ResponseEntity getAllShopRegistrationRequests(@RequestParam("pageNumber") int pageNumber)
    {
        return shopAddingService.getAllShopRegistrationRequests(pageNumber);
    }

    @GetMapping("/getAllRegisteredShops")
    public ResponseEntity getAllRegisteredShops(@RequestParam("pageNumber") int pageNumber)
    {
        return shopAddingService.getAllRegisteredShops(pageNumber);
    }
}
