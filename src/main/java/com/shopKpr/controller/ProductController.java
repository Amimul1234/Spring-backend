package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.OwoProduct;
import com.shopKpr.service.admin_related.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public OwoProduct addProduct(@RequestBody OwoProduct product)
    {
        return productService.saveProduct(product);
    }

    @GetMapping("/getAProduct")
    public OwoProduct getAProduct(@RequestParam(name = "productId") Long productId)
    {
        return productService.getAProduct(productId);
    }

    @GetMapping("/getAllProducts")
    public List<OwoProduct> getAllProducts(@RequestParam(name = "page") int page)
    {
        return productService.getAllProducts(page);
    }

    @GetMapping("/searchProductWithName")
    public List<OwoProduct> searchProduct_admin(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProductAdmin(page, product_name);
    }

    @PutMapping("/updateProduct")
    public OwoProduct updateProductInformation(@RequestBody OwoProduct product)
    {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam("productId") Long productId)
    {
        productService.deleteProduct(productId);
    }

    @GetMapping("/getProductByCategories") //This is for getting products via specific categories in Ascending  order
    public List<OwoProduct> getProductByCategories(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") Long[] product_categories)
    {
        List<Long> categories = Arrays.asList(product_categories);
        return productService.getProduct_by_categories(page, categories);
    }

    @GetMapping("/getProductById")
    public OwoProduct getProductById(@RequestParam(name = "productId") Long productId)
    {
        return productService.getProductById(productId);
    }

    @GetMapping("/getBrandNameViaProductId")
    public String getBrandNameViaProductId(@RequestParam(name = "productId") Long productId)
    {
        return productService.getABrandViaProduct(productId);
    }
}
