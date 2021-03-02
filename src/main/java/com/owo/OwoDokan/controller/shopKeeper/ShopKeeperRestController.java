package com.owo.OwoDokan.controller.shopKeeper;

import com.owo.OwoDokan.ModelClass.CartListFromClient;
import com.owo.OwoDokan.entity.admin_related.OwoProduct;
import com.owo.OwoDokan.entity.admin_related.ShopPendingRequest;
import com.owo.OwoDokan.entity.admin_related.cart.CartListProduct;
import com.owo.OwoDokan.entity.admin_related.order.Shop_keeper_orders;
import com.owo.OwoDokan.service.admin_related.BrandsService;
import com.owo.OwoDokan.service.admin_related.ProductService;
import com.owo.OwoDokan.service.admin_related.cart.Shop_keeper_cart_service;
import com.owo.OwoDokan.service.admin_related.order.Shop_keeper_order_service;
import com.owo.OwoDokan.service.shop_keeper_related.registration.RegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
public class ShopKeeperRestController {

    private final ProductService productService;
    private final BrandsService brandsService;
    private final Shop_keeper_cart_service shop_keeper_cartService;
    private final Shop_keeper_order_service shop_keeper_orderService;
    private final RegistrationService registrationService;

    public ShopKeeperRestController(ProductService productService, BrandsService brandsService, Shop_keeper_cart_service shop_keeper_cartService, Shop_keeper_order_service shop_keeper_orderService, RegistrationService registrationService) {
        this.productService = productService;
        this.brandsService = brandsService;
        this.shop_keeper_cartService = shop_keeper_cartService;
        this.shop_keeper_orderService = shop_keeper_orderService;
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

    @PostMapping("/shop_keeper_order")
    public ResponseEntity add_shop_order(@RequestBody Shop_keeper_orders shop_keeper_orders, @RequestParam(name = "mobile_number") String mobile_number)
    {
        return shop_keeper_orderService.addOrder(shop_keeper_orders, mobile_number);
    }

    @GetMapping("/get_shop_keeper_order")
    public List<Shop_keeper_orders> getShopKeeperOrders(@RequestParam(name = "page") int page, @RequestParam(name = "mobile_number") String mobile_number)
    {
        Page<Shop_keeper_orders> pagedList = shop_keeper_orderService.getAllProducts(page, mobile_number);
        return pagedList.getContent();
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

    @GetMapping("/getBrandsViaCategory")
    public ResponseEntity getBrandsViaCategory(@RequestParam(name = "page") int page, @RequestParam(name = "product_categories") String[] product_categories)
    {
        List<String> categories = Arrays.asList(product_categories);
        return brandsService.getBrandsViaCategory(page, categories);
    }

     */
}