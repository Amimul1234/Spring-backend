package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.repository.adminRelated.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public ResponseEntity approveNewShop(Shops shops)
    {
        try {
            shopRepository.save(shops);
            return ResponseEntity.status(OK).body("Shop approved successfully");
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not approve shop");
        }
    }

    public ResponseEntity updateShop(Shops shops) {
        try
        {
            shopRepository.save(shops);
            return ResponseEntity.status(OK).body(shops);
        }
        catch (Exception e)
        {
            logger.error("Error Occurred On Shop Adding Service, Error is: "+e.getMessage());
            return new ResponseEntity(FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getShopInfo(String shop_phone) {
        Optional<Shops> shopsOptional = shopRepository.getByPhone(shop_phone);
        return shopsOptional.map(shops -> ResponseEntity.status(OK).body(shops)).orElseGet(() -> new ResponseEntity(NOT_FOUND));
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
