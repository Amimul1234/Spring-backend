package com.owo.OwoDokan.controller.shopKeeper;

import com.owo.OwoDokan.service.shop_keeper_related.Debt.Shop_offer_service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopKeeperOfferController {
    private final Shop_offer_service shop_offer_service;

    public ShopKeeperOfferController(Shop_offer_service shop_offer_service) {
        this.shop_offer_service = shop_offer_service;
    }

    @PutMapping
    public ResponseEntity addShopOffer(@RequestParam(name = "mobile_number") String mobile_number,
                                       @RequestParam(name = "offer_uri") String offer_uri)
    {
        return shop_offer_service.addOffer(mobile_number, offer_uri);
    }

    @GetMapping
    public ResponseEntity getShopOffers(@RequestParam(name = "mobile_number") String mobile_number)
    {
        return shop_offer_service.getAllOffers(mobile_number);
    }

    @DeleteMapping
    public ResponseEntity deleteShopOffer(@RequestParam(name = "mobile_number") String mobile_number,
                                          @RequestParam(name = "offer_id") long offer_id)
    {
        return shop_offer_service.deleteOffer(mobile_number, offer_id);
    }
}
