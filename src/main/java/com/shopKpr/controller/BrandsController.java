package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.Brands;
import com.shopKpr.service.admin_related.BrandsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BrandsController {
    private final BrandsService brandsService;

    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }

    @GetMapping("/getAllBrandsOfASubCategory")
    public List<Brands> getAllBrands(@RequestParam("subCategoryId") Long subCategoryId)
    {
        return brandsService.getAllBrands(subCategoryId);
    }

    @PostMapping("/addABrand")
    public void addBrand(@RequestBody Brands brands)
    {
        brandsService.createBrand(brands);
    }

    @PutMapping("/updateBrand")
    public String updateBrand(@RequestParam(name = "subCategoryId") Long subCategoryId, @RequestBody Brands brands)
    {
        return brandsService.updateBrand(subCategoryId, brands);
    }

    @DeleteMapping("/deleteBrand")
    public String deleteBrand(@RequestParam(name = "subCategoryId") Long subCategoryId, @RequestParam(name = "brandsId") Long brandsId)
    {
        return brandsService.deleteBrand(subCategoryId, brandsId);
    }

    @GetMapping("/getBrandsViaCategory")
    public List<Brands> getBrandsViaCategory(@RequestParam(name = "number") int number, @RequestParam(name = "categoryIds") List<Long> categoryIds)
    {
        return brandsService.getBrandsViaCategory(number, categoryIds);
    }
}
