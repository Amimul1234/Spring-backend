package com.owo.OwoDokan.controller.shopKeeper;

import com.owo.OwoDokan.ResponseManipulation.Product_data_manipulation;
import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.entity.admin_related.Owo_product;
import com.owo.OwoDokan.service.admin_related.BrandsService;
import com.owo.OwoDokan.service.admin_related.ProductService;
import org.springframework.data.domain.Page;
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

    //Customer and shop keeper section

    @GetMapping("/allProducts")
    public Product_data_manipulation getAllProduct(@RequestParam(name = "page") int page)
    {
        Page<Owo_product> pagedList = productService.getAllProducts(page);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();
        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }
        return product_data_manipulation;
    }

    @GetMapping("/getProductByCategory") //This is for getting products via category
    public Product_data_manipulation getProductByCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_category(page, product_category);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductByCategories") //This is for getting products via specific categories in Ascending  order
    public Product_data_manipulation getProductByCategories(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories)
    {
        List<String> categories = Arrays.asList(product_categories);
        Page<Owo_product> pagedList = productService.getProduct_by_categories(page, categories);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductByCategoryDesc") //This is for getting products via category DESC
    public Product_data_manipulation getProductByCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_category") String product_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_categoryDesc(page, product_category);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }
        return product_data_manipulation;
    }

    @GetMapping("/getProductByCategoriesDesc") //This is for getting products via specific categories in Descending  order for shopkeeper
    public Product_data_manipulation getProductByCategoriesDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_category)
    {
        List<String> categories = Arrays.asList(product_category);
        Page<Owo_product> pagedList = productService.getProduct_by_categoriesDesc(page, categories);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductBySubCategory") //This is for getting products via category
    public Product_data_manipulation getProductBySubCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_sub_category(page, product_sub_category);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductBySubCategoryDesc") //This is for getting products via category
    public Product_data_manipulation getProductBySubCategoryDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_sub_category") String product_sub_category)
    {
        Page<Owo_product> pagedList = productService.getProduct_by_sub_categoryDesc(page, product_sub_category);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductById")
    public Owo_product getProductById(@RequestParam(name = "id") int id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/searchProduct")
    public Product_data_manipulation searchProduct(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {

        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(productService.searchProduct(page, product_categories, product_name));
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;

    }

    @GetMapping("/searchProductDesc")
    public Product_data_manipulation searchProductDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_name") String product_name)
    {
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(productService.searchProductDesc(page, product_categories, product_name));
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;

    }

    @GetMapping("/getProductByBrand") //This is for getting products via category
    public Product_data_manipulation getProductByBrand(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        Page<Owo_product> pagedList = productService.getProductByBrand(page, product_brand, product_categories);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getProductByBrandDesc") //This is for getting products via category
    public Product_data_manipulation getProductByBrandDesc(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories, @RequestParam(name = "product_brand") String product_brand)
    {
        Page<Owo_product> pagedList = productService.getProductByBrandDesc(page, product_brand, product_categories);
        Product_data_manipulation product_data_manipulation = new Product_data_manipulation();

        try
        {
            product_data_manipulation.products.addAll(pagedList.getContent());
            product_data_manipulation.error = false;
        } catch (Exception e) {
            product_data_manipulation.error = true;
        }

        return product_data_manipulation;
    }

    @GetMapping("/getBrandsViaCategory")
    public List<Brands> getBrandsViaCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories)
    {
        List<String> categories = Arrays.asList(product_categories);
        Page<Brands> pagedList = brandsService.getBrandsViaCategory(page, categories);
        return pagedList.getContent();
    }


    /*
    Admin Section beginning here

           ----------------***********************************------------------------------------------
           -------------------*****************************---------------------------------------------
           ----------------------***********************------------------------------------------------
           --------------------------***************----------------------------------------------------

     */

}