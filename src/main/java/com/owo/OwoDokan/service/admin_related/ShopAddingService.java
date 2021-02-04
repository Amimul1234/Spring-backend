package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.repository.adminRelated.ShopRepository;
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

    public Shops getShopInfo(String shop_phone) {
        return shopRepository.getByPhone(shop_phone);
    }
}
