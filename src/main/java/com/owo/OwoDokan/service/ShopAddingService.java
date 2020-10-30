package com.owo.OwoDokan.service;

import com.owo.OwoDokan.entity.Shops;
import com.owo.OwoDokan.repository.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopAddingService {
    private final ShopRepository shopRepository;

    public ShopAddingService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shops approveNewShop(Shops shops)
    {
        return shopRepository.save(shops);
    }

    public Shops updateShop(Shops shops) {
        return shopRepository.save(shops);
    }
}
