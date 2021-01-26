package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.repository.admin_related.BrandsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity getBrandsViaCategory(int page, List<String>product_categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            return new ResponseEntity(brandsRepository.findBrandViaCategories(product_categories, pageable).getContent(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
