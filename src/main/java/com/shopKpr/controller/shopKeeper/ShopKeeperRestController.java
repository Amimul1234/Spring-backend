package com.shopKpr.controller.shopKeeper;

import com.shopKpr.ModelClass.CartListFromClient;
import com.shopKpr.entity.admin_related.ShopPendingRequest;
import com.shopKpr.entity.admin_related.cart.CartListProduct;
import com.shopKpr.service.admin_related.cart.Shop_keeper_cart_service;
import com.shopKpr.service.shop_keeper_related.registration.RegistrationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ShopKeeperRestController {
    private final Shop_keeper_cart_service shop_keeper_cartService;
    private final RegistrationService registrationService;

    public ShopKeeperRestController(Shop_keeper_cart_service shop_keeper_cartService, RegistrationService registrationService) {
        this.shop_keeper_cartService = shop_keeper_cartService;
        this.registrationService = registrationService;
    }

    //Shop Registration Request
    @PostMapping("/shopRegisterRequest")
    public String shopRegisterRequest(@RequestBody ShopPendingRequest shopPendingRequest)
    {
        return registrationService.addNewRequest(shopPendingRequest);
    }

    @PostMapping("/shopKeeperCart")
    public String cartListItems(@RequestBody CartListFromClient cartListFromClient)
    {
        return shop_keeper_cartService.addCartItem(cartListFromClient);
    }

    @GetMapping("/shop_keeper_cart_products")
    public List<CartListProduct> getCartListForShopKeeper(@RequestParam(name = "mobile_number") String mobile_number) throws Exception {
        return shop_keeper_cartService.getCartItems(mobile_number);
    }

    @PutMapping("/update_cart_list")
    public CartListProduct updateCartProduct(@RequestBody CartListProduct cart_list_product, @RequestParam(name = "mobile_number") String mobile_number)
    {
        return shop_keeper_cartService.updateCartItem(cart_list_product, mobile_number);
    }

    @DeleteMapping("/delete_cart_product")
    public void deleteCartProduct(@RequestParam(name = "product_id") int product_id, @RequestParam(name = "mobile_number") String mobile_number)
    {
        shop_keeper_cartService.deleteCartProduct(product_id, mobile_number);
    }

    /*
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

     */
}