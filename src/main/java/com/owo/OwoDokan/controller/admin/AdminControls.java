package com.owo.OwoDokan.controller.admin;

import com.owo.OwoDokan.entity.admin_related.*;
import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.entity.admin_related.registerAccount.ShopKeeperUser;
import com.owo.OwoDokan.entity.admin_related.registerAccount.UserShopKeeper;
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
    @GetMapping("/getShopKeeper")
    public ShopKeeperUser getShopKeeper(@RequestParam(name = "mobileNumber") String mobileNumber)
    {
        return shopKeeperRegistrationService.getShopKeeper(mobileNumber);
    }

    @GetMapping("/getAllEnabledShopKeepers")
    public List<ShopKeeperUser> getAllEnabledShopKeepers(@RequestParam(name = "page") int page)
    {
        return shopKeeperRegistrationService.findAllEnabledShopKeeper(page);
    }

    @GetMapping("/getAllDisabledAccounts")
    public List<ShopKeeperUser> getAllDisabledAccounts(@RequestParam(name = "page") int page)
    {
        return shopKeeperRegistrationService.findAllDisabledShopKeeper(page);
    }

    @PostMapping("/registerShopKeeper")
    public String registerShopKeeper(@RequestBody UserShopKeeper userShopKeeper)
    {
        return shopKeeperRegistrationService.addNewShopKeeper(userShopKeeper);
    }


    //Shop Management
    @PostMapping("/approveShop")
    public Shops approveShop(@RequestBody Shops shops)
    {
        return shopAddingService.approveNewShop(shops);
    }

    @PostMapping("/updateShopInfo")
    public Shops updateShop(@RequestBody Shops  shops)
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
    @GetMapping("/getAllProducts")
    public List<OwoProduct> getAllProducts(@RequestParam(name = "page") int page)
    {
        return productService.getAllProducts(page);
    }

    @GetMapping("/getAProduct")
    public OwoProduct getAProduct(@RequestParam(name = "productId") Long productId)
    {
        return productService.getAProduct(productId);
    }

    @PostMapping("/addProduct") //This method is for adding new products
    public OwoProduct addProduct(@RequestBody OwoProduct product)
    {
        return productService.saveProduct(product);
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

    @GetMapping("/searchProductWithName")
    public List<OwoProduct> searchProduct_admin(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "product_name") String product_name)
    {
        return productService.searchProductAdmin(page, product_name);
    }

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
    public ResponseEntity<List<Shop_keeper_orders>> getDeliveredOrders(@RequestParam("page_num") int page_num)
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
    public ResponseEntity<List<Shop_keeper_orders>> getCancelledOrders(@RequestParam("page_num") int page_num)
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
    @GetMapping("/getAllOffers")
    public List<OffersEntity> getAllOffers()
    {
        return offerService.getAllOffers();
    }

    @PostMapping("/addAnOffer")
    public String addAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.addANewOffer(offersEntity);
    }

    @PutMapping("/updateAnOffer")
    public String updateAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.updateExistenceOffer(offersEntity);
    }

    @DeleteMapping("/deleteOffer")
    public String deleteAnOffer(@RequestParam(name = "offerId") Long offerId)
    {
        return offerService.deleteOffer(offerId);
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
    public String updateCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody CategoryEntity categoryEntity)
    {
        return categoryService.updateCategory(categoryId, categoryEntity);
    }

    @DeleteMapping("/deleteCategory")
    public String deleteCategory(@RequestParam(name = "categoryId") Long categoryId)
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
}
