package com.owo.OwoDokan.controller.admin;

import com.owo.OwoDokan.entity.admin_related.*;
import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.service.admin_related.*;
import com.owo.OwoDokan.service.admin_related.category.CategoryService;
import com.owo.OwoDokan.service.admin_related.category.SubCategoryService;
import com.owo.OwoDokan.service.admin_related.order.Shop_keeper_order_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AdminControls {

    private final ProductService productService;
    private final ShopAddingService shopAddingService;
    private final BrandsService brandsService;
    private final Shop_keeper_order_service shop_keeper_orderService;
    private final OfferService offerService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final ShopKeeperRegistrationService shopKeeperRegistrationService;

    public AdminControls(ProductService productService, ShopAddingService shopAddingService, BrandsService brandsService,
                         Shop_keeper_order_service shop_keeper_orderService, OfferService offerService,
                         CategoryService categoryService, SubCategoryService subCategoryService,
                         ShopKeeperRegistrationService shopKeeperRegistrationService)
    {
        this.productService = productService;
        this.shopAddingService = shopAddingService;
        this.brandsService = brandsService;
        this.shop_keeper_orderService = shop_keeper_orderService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.shopKeeperRegistrationService = shopKeeperRegistrationService;
    }

    //Shop Keeper Management
    @PostMapping("/registerShopKeeper")
    public ResponseEntity registerShopKeeper(@RequestBody UserShopKeeper userShopKeeper)
    {
        return shopKeeperRegistrationService.addNewShopKeeper(userShopKeeper);
    }

    @GetMapping("/getShopKeeper")
    public ResponseEntity getShopKeeper(@RequestParam(name = "mobile_number") String mobile_number)
    {
        return shopKeeperRegistrationService.getShopKeeper(mobile_number);
    }

    //Shop Management
    @PostMapping("/approveShop")
    public Shops approveShop(@RequestBody Shops shops)
    {
        return shopAddingService.approveNewShop(shops);
    }

    @PostMapping("/updateShopInfo")
    public ResponseEntity updateShop(@RequestBody Shops  shops)
    {
        return shopAddingService.updateShop(shops);
    }

    @GetMapping("/getShopInfo")
    public ResponseEntity get_shop_info(@RequestParam(name = "shop_phone") String shop_phone) {
        return shopAddingService.getShopInfo(shop_phone);
    }

    @GetMapping("/getAllShopRegistrationRequests")
    public ResponseEntity getAllShopRegistrationRequests(@RequestParam("pageNumber") int pageNumber)
    {
        return shopAddingService.getAllShopRegistrationRequests(pageNumber);
    }

    @GetMapping("/getAllRegisteredShops")
    public ResponseEntity getAllRegisteredShops(@RequestParam("pageNumber") int pageNumber)
    {
        return shopAddingService.getAllRegisteredShops(pageNumber);
    }

    //Product Management
    @PostMapping("/addProduct") //This method is for adding new products
    public OwoProduct addProduct(@RequestBody OwoProduct product)
    {
        return productService.saveProduct(product);
    }

    /*
    @PutMapping("/updateProduct")
    public OwoProduct updateProductInformation(@RequestBody OwoProduct product)
    {
        return productService.updateProduct(product);
    }

    @GetMapping("/searchProduct_admin")
    public ResponseEntity searchProduct_admin(@RequestParam(name = "page") int page, @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProductAdmin(page, product_name);
    }

    @DeleteMapping("/deleteProduct/{product_id}")
    public void deleteProduct(@PathVariable("product_id") String product_id)
    {
        productService.deleteProduct(product_id);
    }

     */

    //Order Management
    @GetMapping("/getPendingOrders")
    public ResponseEntity getPendingOrders()
    {
        return shop_keeper_orderService.findAllByState("Pending");
    }

    @GetMapping("/getConfirmedOrders")
    public ResponseEntity getConfirmedOrders()
    {
        return shop_keeper_orderService.findAllByState("Confirmed");
    }

    @GetMapping("/getProcessingOrders")
    public ResponseEntity getProcessingOrders()
    {
        return shop_keeper_orderService.findAllByState("Processing");
    }

    @GetMapping("/getPickedOrders")
    public ResponseEntity getPickedOrders()
    {
        return shop_keeper_orderService.findAllByState("Picked");
    }

    @GetMapping("/getShippedOrders")
    public ResponseEntity getShippedOrders()
    {
        return shop_keeper_orderService.findAllByState("Shipped");
    }

    @GetMapping("/getDeliveredOrders")
    public ResponseEntity<List<com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders>> getDeliveredOrders(@RequestParam("page_num") int page_num)
    {
        try
        {
            return new ResponseEntity<>(shop_keeper_orderService.findDeliveredOrders(page_num).getContent(), HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @GetMapping("/getCancelledOrders")
    public ResponseEntity<List<com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders>> getCancelledOrders(@RequestParam("page_num") int page_num)
    {
        try
        {
            return new ResponseEntity<>(shop_keeper_orderService.findCancelledOrders(page_num).getContent(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PutMapping("/setOrderState")
    public ResponseEntity setOrderState(@RequestParam(name="order_id") long order_id, @RequestParam("order_state") String order_state)
    {
        return shop_keeper_orderService.setOrderState(order_id, order_state);
    }

    //Offer management
    @PostMapping("/addAnOffer")
    public ResponseEntity addAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.addANewOffer(offersEntity);
    }

    @PutMapping("/updateAnOffer")
    public ResponseEntity updateAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.updateExistenceOffer(offersEntity);
    }

    @DeleteMapping("/deleteOffer")
    public ResponseEntity deleteAnOffer(@RequestParam(name = "offer_id") Long offer_id)
    {
        return offerService.deleteOffer(offer_id);
    }

    //Category Management
    @PostMapping("/addNewCategory")
    public String addNewCategory(@RequestBody CategoryEntity categoryEntity)
    {
        return categoryService.addNewCategory(categoryEntity);
    }

    @GetMapping("/getAllCategories")
    public List<CategoryEntity> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @PutMapping("/updateCategory")
    public ResponseEntity updateCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody CategoryEntity categoryEntity)
    {
        return categoryService.updateCategory(categoryId, categoryEntity);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity deleteCategory(@RequestParam(name = "categoryId") Long categoryId)
    {
        return categoryService.deleteCategory(categoryId);
    }

    //Subcategory Management
    @PostMapping("/addNewSubCategory")
    public String addNewSubCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody SubCategoryEntity subCategoryEntity)
    {
        return subCategoryService.addNewSubCategory(categoryId, subCategoryEntity);
    }

    @GetMapping("/getAllSubCategories")
    public List<SubCategoryEntity> getAllSubCategories(@RequestParam(name = "categoryId") Long categoryId)
    {
        return subCategoryService.getAllSubCategories(categoryId);
    }

    @PutMapping("/updateSubCategory")
    public String updateSubCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody SubCategoryEntity subCategoryEntity)
    {
        return subCategoryService.updateSubCategory(categoryId, subCategoryEntity);
    }

    @DeleteMapping("/deleteSubCategory")
    public String deleteSubCategory(@RequestParam(name = "subCategoryId") Long subCategoryId)
    {
        return subCategoryService.deleteSubCategory(subCategoryId);
    }

    //Brands Management
    @PostMapping("/addABrand")
    public void addBrand(@RequestBody Brands brands)
    {
        brandsService.createBrand(brands);
    }

    @GetMapping("/getAllBrandsOfASubCategory")
    public List<Brands> getAllBrands(@RequestParam("subCategoryId") Long subCategoryId)
    {
        return brandsService.getAllBrands(subCategoryId);
    }

    @PutMapping("/updateBrand")
    public String updateBrand(@RequestParam(name = "subCategoryId") Long subCategoryId, @RequestBody Brands brands)
    {
        return brandsService.updateBrand(subCategoryId, brands);
    }

}
