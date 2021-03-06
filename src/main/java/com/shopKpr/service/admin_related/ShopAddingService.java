package com.shopKpr.service.admin_related;

import com.shopKpr.entity.admin_related.Shops;
import com.shopKpr.exceptions.ShopNotFoundException;
import com.shopKpr.repository.adminRelated.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.HttpStatus.*;

@Service
public class ShopAddingService {
    private final ShopRepository shopRepository;
    private final Logger logger = LoggerFactory.getLogger(ShopAddingService.class);

    public ShopAddingService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Transactional
    public Shops approveNewShop(Shops shops)
    {
        shopRepository.save(shops);
        return shops;
    }

    @Transactional
    public Shops updateShop(Shops shops) {
        try
        {
            shopRepository.save(shops);
            return shops;
        }
        catch (Exception e)
        {
            logger.error("Error Occurred On Shop Adding Service, Error is: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Shops getShopInfo(String shop_phone) {

        Optional<Shops> shopsOptional = shopRepository.getByPhone(shop_phone);

        if(shopsOptional.isPresent())
        {
            return shopsOptional.get();
        }
        else
        {
            logger.info("Shop with phone : "+shop_phone + " Not found");
            throw new ShopNotFoundException(shop_phone);
        }
    }

    public ResponseEntity getAllShopRegistrationRequests(int pageNumber) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Optional<List<Shops>> optionalShopsList = shopRepository.getAllShopRegistrationRequests(pageable);
        return optionalShopsList.map(shops -> ResponseEntity.status(OK).body(shops)).orElseGet(() -> new ResponseEntity(NOT_FOUND));
    }

    public ResponseEntity getAllRegisteredShops(int pageNumber) {
        int pageSize = 10;
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Optional<List<Shops>> optionalShopsList = shopRepository.getAllRegisteredShops(pageable);
        return optionalShopsList.map(shops -> ResponseEntity.status(OK).body(shops)).orElseGet(() -> new ResponseEntity(NOT_FOUND));
    }
}
