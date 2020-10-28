package com.owo.OwoDokan.controller;

import com.owo.OwoDokan.entity.Owo_product;
import com.owo.OwoDokan.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct") //This method is for adding new products
    public Owo_product addProduct(@RequestBody Owo_product product)
    {
        return productService.saveProduct(product);
    }

    @GetMapping("/allProducts")
    public List<Owo_product> getAllProduct(@RequestParam(name = "page") int page)
    {
        Page<Owo_product> pagedList = productService.getAllProducts(page);
        return pagedList.getContent();
    }

    @GetMapping("/getProductByCategory") //This is for getting products via category
    public List<Owo_product> getProductByCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_category(page, product_category);
        return pagedList.getContent();
    }

    @GetMapping("/getProductByCategories") //This is for getting products via specific categories in Ascending  order
    public List<Owo_product> getProductByCategories(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_category)
    {
        List<String> categories = Arrays.asList(product_category);
        Page<Owo_product> pagedList = productService.getProduct_by_categories(page, categories);
        return pagedList.getContent();
    }

    @GetMapping("/getProductByCategoryDesc") //This is for getting products via category DESC
    public List<Owo_product> getProductByCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_categoryDesc(page, product_category);
        return pagedList.getContent();
    }

    @GetMapping("/getProductByCategoriesDesc") //This is for getting products via specific categories in Descending  order for shopkeeper
    public List<Owo_product> getProductByCategoriesDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_category)
    {
        List<String> categories = Arrays.asList(product_category);
        Page<Owo_product> pagedList = productService.getProduct_by_categoriesDesc(page, categories);
        return pagedList.getContent();
    }

    @GetMapping("/getProductBySubCategory") //This is for getting products via category
    public List<Owo_product> getProductBySubCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_sub_category(page, product_sub_category);
        return pagedList.getContent();
    }

    @GetMapping("/getProductBySubCategoryDesc") //This is for getting products via category
    public List<Owo_product> getProductBySubCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_sub_categoryDesc(page, product_sub_category);
        return pagedList.getContent();
    }

    @GetMapping("/getProductById")
    public Owo_product getProductById(@RequestParam(name = "id") int id)
    {
        return productService.getProductById(id);
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

    @GetMapping("/searchProduct")
    public List<Owo_product> searchProduct(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProduct(page, product_categories, product_name);
    }

    @GetMapping("/searchProductDesc")
    public List<Owo_product> searchProductDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProductDesc(page, product_categories, product_name);
    }

    @GetMapping("/getProductByBrand") //This is for getting products via category
    public List<Owo_product> getProductByBrand(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        Page<Owo_product> pagedList = productService.getProductByBrand(page, product_brand, product_categories);
        return pagedList.getContent();
    }

    @GetMapping("/getProductByBrandDesc") //This is for getting products via category
    public List<Owo_product> getProductByBrandDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        Page<Owo_product> pagedList = productService.getProductByBrandDesc(page, product_brand, product_categories);
        return pagedList.getContent();
    }

}