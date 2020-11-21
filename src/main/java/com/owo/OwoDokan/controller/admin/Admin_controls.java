package com.owo.OwoDokan.controller.admin;

import com.owo.OwoDokan.ResponseManipulation.Product_data_manipulation;
import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.entity.admin_related.Owo_product;
import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.service.admin_related.BrandsService;
import com.owo.OwoDokan.service.admin_related.ProductService;
import com.owo.OwoDokan.service.admin_related.ShopAddingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class Admin_controls {

    private final ProductService productService;
    private final ShopAddingService shopAddingService;
    private final BrandsService brandsService;

    public Admin_controls(ProductService productService, ShopAddingService shopAddingService, BrandsService brandsService) {
        this.productService = productService;
        this.shopAddingService = shopAddingService;
        this.brandsService = brandsService;
    }

    @PostMapping("/addProduct") //This method is for adding new products
    public Owo_product addProduct(@RequestBody Owo_product product)
    {
        return productService.saveProduct(product);
    }

    @PostMapping("/approveShop") //This method is for adding new products
    public Shops approveShop(@RequestBody Shops shops)
    {
        return shopAddingService.approveNewShop(shops);
    }

    @PostMapping("/updateShopInfo") //This method is for adding new products
    public Shops updateShop(@RequestBody Shops shops)
    {
        return shopAddingService.updateShop(shops);
    }

    @GetMapping("/getShopInfo")
    public Shops get_shop_info(@RequestParam(name = "shop_phone") String shop_phone)
    {
        return shopAddingService.getShopInfo(shop_phone);
    }

    @PutMapping("/updateProduct")
    public Owo_product updateProductInformation(@RequestBody Owo_product product)
    {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/deleteProduct/{product_id}")
    public void deleteProduct(@PathVariable("product_id") String product_id)
    {
        productService.deleteProduct(product_id);
    }

    @PostMapping("/addABrand")
    public void addBrand(@RequestBody Brands brands)
    {
        brandsService.createBrand(brands);
    }

    @GetMapping("/getBrandsAdmin")
    public List<String> brandsAdmin(@RequestParam(name = "category") String category)
    {
        return brandsService.getBrandsAdmin(category);
    }

    @GetMapping("/searchProduct_admin")
    public Product_data_manipulation searchProduct_admin(@RequestParam(name = "page") int page, @RequestParam(name = "product_name") String product_name)
    {
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();
        try
        {
            product_data_manipulation.products.addAll(productService.searchProductAdmin(page, product_name));
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }
        return product_data_manipulation;
    }

}
