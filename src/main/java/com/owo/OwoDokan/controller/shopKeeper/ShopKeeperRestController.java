package com.owo.OwoDokan.controller.shopKeeper;

import com.owo.OwoDokan.service.admin_related.BrandsService;
import com.owo.OwoDokan.service.admin_related.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
public class ShopKeeperRestController {

    private final ProductService productService;
    private final BrandsService brandsService;

    public ShopKeeperRestController(ProductService productService, BrandsService brandsService) {
        this.productService = productService;
        this.brandsService = brandsService;
    }

    @GetMapping("/allProducts")
    public ResponseEntity getAllProduct(@RequestParam(name = "page") int page)
    {
        return productService.getAllProducts(page);
    }

    @GetMapping("/getProductByCategory") //This is for getting products via category
    public ResponseEntity getProductByCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        return productService.getProduct_by_category(page, product_category);
    }

    @GetMapping("/getProductByCategoryDesc") //This is for getting products via category DESC
    public ResponseEntity getProductByCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        return productService.getProduct_by_categoryDesc(page, product_category);
    }

    @GetMapping("/getProductByCategories") //This is for getting products via specific categories in Ascending  order
    public ResponseEntity getProductByCategories(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories)
    {
        List<String> categories = Arrays.asList(product_categories);
        return productService.getProduct_by_categories(page, categories);
    }

    @GetMapping("/getProductByCategoriesDesc") //This is for getting products via specific categories in Descending  order for shopkeeper
    public ResponseEntity getProductByCategoriesDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_category)
    {
        List<String> categories = Arrays.asList(product_category);
        return productService.getProduct_by_categoriesDesc(page, categories);
    }

    @GetMapping("/getProductBySubCategory") //This is for getting products via category
    public ResponseEntity getProductBySubCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        return productService.getProduct_by_sub_category(page, product_sub_category);
    }

    @GetMapping("/getProductBySubCategoryDesc") //This is for getting products via category
    public ResponseEntity getProductBySubCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        return productService.getProduct_by_sub_categoryDesc(page, product_sub_category);
    }

    @GetMapping("/getProductById")
    public ResponseEntity getProductById(@RequestParam(name = "id") int id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity searchProduct(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProduct(page, product_categories, product_name);
    }

    @GetMapping("/searchProductDesc")
    public ResponseEntity searchProductDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProductDesc(page, product_categories, product_name);
    }

    @GetMapping("/getProductByBrand") //This is for getting products via category
    public ResponseEntity getProductByBrand(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        return productService.getProductByBrand(page, product_brand, product_categories);
    }

    @GetMapping("/getProductByBrandDesc") //This is for getting products via category
    public ResponseEntity getProductByBrandDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        return productService.getProductByBrandDesc(page, product_brand, product_categories);
    }

    @GetMapping("/getBrandsViaCategory")
    public ResponseEntity getBrandsViaCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories)
    {
        List<String> categories = Arrays.asList(product_categories);
        return brandsService.getBrandsViaCategory(page, categories);
    }
}