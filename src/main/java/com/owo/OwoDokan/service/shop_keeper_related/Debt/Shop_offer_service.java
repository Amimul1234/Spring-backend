package com.owo.OwoDokan.service.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.shopKeeper_related.Shop_offers;
import com.owo.OwoDokan.repository.adminRelated.ShopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Shop_offer_service {
    private final ShopRepository shopRepository;

    public Shop_offer_service(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public ResponseEntity addOffer(String mobile_number, String offer_uri) {
        Shops shops;
        try
        {
            shops = shopRepository.getByPhone(mobile_number);

            Shop_offers shop_offers = new Shop_offers();
            shop_offers.setOffer_imag_uri(offer_uri);
            shop_offers.setShops(shops);

            shops.getShopOffersList().add(shop_offers);

            shopRepository.save(shops);
            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getAllOffers(String mobile_number) {
        Shops shops;

        try
        {
            shops = shopRepository.getByPhone(mobile_number);
            return new ResponseEntity(shops.getShopOffersList(), HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }


    public ResponseEntity deleteOffer(String mobile_number, long offer_id) {
        Shops shop;
        Shop_offers shop_offers = null;

        try
        {
            shop = shopRepository.getByPhone(mobile_number);

            for(Shop_offers shop_offers1 : shop.getShopOffersList())
            {
                if(shop_offers1.getOffer_id() == offer_id)
                {
                    shop_offers = shop_offers1;
                    break;
                }
            }

            shop.getShopOffersList().remove(shop_offers);

            shopRepository.save(shop);
            return new ResponseEntity(HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
