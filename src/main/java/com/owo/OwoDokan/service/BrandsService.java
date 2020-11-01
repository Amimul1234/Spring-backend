package com.owo.OwoDokan.service;

import com.owo.OwoDokan.entity.Brands;
import com.owo.OwoDokan.repository.BrandsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandsService {
    private final BrandsRepository brandsRepository;

    public BrandsService(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    public void createBrand(Brands brands) {
        brandsRepository.save(brands);
    }

    public List<String> getBrandsAdmin(String category) {
        return brandsRepository.getBrands(category);
    }

    public Page<Brands> getBrandsViaCategory(int page, List<String>product_categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return brandsRepository.findBrandViaCategories(product_categories, pageable);
    }
}
