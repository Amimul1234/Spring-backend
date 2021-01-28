package com.owo.OwoDokan.controller.admin;

import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.entity.admin_related.OffersEntity;
import com.owo.OwoDokan.entity.admin_related.Owo_product;
import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.service.admin_related.BrandsService;
import com.owo.OwoDokan.service.admin_related.OfferService;
import com.owo.OwoDokan.service.admin_related.ProductService;
import com.owo.OwoDokan.service.admin_related.ShopAddingService;
import com.owo.OwoDokan.service.admin_related.order.Shop_keeper_order_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
public class Admin_controls {

    private final ProductService productService;
    private final ShopAddingService shopAddingService;
    private final BrandsService brandsService;
    private final Shop_keeper_order_service shop_keeper_orderService;
    private final OfferService offerService;

    public Admin_controls(ProductService productService, ShopAddingService shopAddingService, BrandsService brandsService, Shop_keeper_order_service shop_keeper_orderService, OfferService offerService) {
        this.productService = productService;
        this.shopAddingService = shopAddingService;
        this.brandsService = brandsService;
        this.shop_keeper_orderService = shop_keeper_orderService;
        this.offerService = offerService;
    }

    @PostMapping("/addProduct") //This method is for adding new products
    public Owo_product addProduct(@RequestBody Owo_product product)
    {
        return productService.saveProduct(product);
    }

    @PutMapping("/updateProduct")
    public Owo_product updateProductInformation(@RequestBody Owo_product product)
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

    @GetMapping("/getShopInfo")
    public Shops get_shop_info(@RequestParam(name = "shop_phone") String shop_phone) {
        return shopAddingService.getShopInfo(shop_phone);
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
    public ResponseEntity setOrderState(@RequestParam(name = "order_id") long order_id, @RequestParam("order_state") String order_state)
    {
        return shop_keeper_orderService.setOrder_state(order_id, order_state);
    }

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

    /*
    @PostMapping("/addNewCategory")
    public ResponseEntity addNewCategory(@RequestParam(name = "category_name") String category_name,
                                         @RequestParam(name = "category_image") String category_image)
    {

    }

     */
}
