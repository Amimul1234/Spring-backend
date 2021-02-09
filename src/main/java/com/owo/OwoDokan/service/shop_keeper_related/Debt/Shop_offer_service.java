package com.owo.OwoDokan.service.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.shopKeeper_related.Shop_offers;
import com.owo.OwoDokan.repository.adminRelated.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class Shop_offer_service {

    private final ShopRepository shopRepository;
    private final Logger logger = LoggerFactory.getLogger(Shop_offer_service.class);

    public Shop_offer_service(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public ResponseEntity addOffer(String mobile_number, String offer_uri) {

        Optional<Shops> shopsOptional = shopRepository.getByPhone(mobile_number);

        if(shopsOptional.isPresent())
        {
            Shops shops = shopsOptional.get();
            Shop_offers shop_offers = new Shop_offers();
            shop_offers.setOffer_imag_uri(offer_uri);
            shop_offers.setShops(shops);

            shops.getShopOffersList().add(shop_offers);

            try {
                shopRepository.save(shops);
                return new ResponseEntity(HttpStatus.OK);
            }
            catch (Exception e)
            {
                logger.error(e.getMessage());
                return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
            }

        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find shop");
        }
    }

    public ResponseEntity getAllOffers(String mobile_number) {

        Optional<Shops> shopsOptional = shopRepository.getByPhone(mobile_number);

        if(shopsOptional.isPresent())
        {
            Shops shops = shopsOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(shops.getShopOffersList());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find shop");
        }
    }


    public ResponseEntity deleteOffer(String mobile_number, long offer_id) {

        Shop_offers shop_offers = null;

        Optional<Shops> shopsOptional = shopRepository.getByPhone(mobile_number);

        if(shopsOptional.isPresent())
        {
            Shops shop = shopsOptional.get();

            for(Shop_offers shop_offers1 : shop.getShopOffersList())
            {
                if(shop_offers1.getOffer_id() == offer_id)
                {
                    shop_offers = shop_offers1;
                    break;
                }
            }

            shop.getShopOffersList().remove(shop_offers);

            try {
                shopRepository.save(shop);
                return new ResponseEntity(HttpStatus.OK);
            }catch (Exception e)
            {
                logger.error(e.getMessage());
                return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find shop");
        }
    }
}
