package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.order.Shop_keeper_orders;
import com.shopKpr.service.admin_related.order.ShopKeeperOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderController {

    private final ShopKeeperOrderService shopKeeperOrderService;

    public OrderController(ShopKeeperOrderService shopKeeperOrderService) {
        this.shopKeeperOrderService = shopKeeperOrderService;
    }

    @GetMapping("/getPendingOrders")
    public ResponseEntity getPendingOrders()
    {
        return shopKeeperOrderService.findAllByState("Pending");
    }

    @GetMapping("/getConfirmedOrders")
    public ResponseEntity getConfirmedOrders()
    {
        return shopKeeperOrderService.findAllByState("Confirmed");
    }

    @GetMapping("/getProcessingOrders")
    public ResponseEntity getProcessingOrders()
    {
        return shopKeeperOrderService.findAllByState("Processing");
    }

    @GetMapping("/getPickedOrders")
    public ResponseEntity getPickedOrders()
    {
        return shopKeeperOrderService.findAllByState("Picked");
    }

    @GetMapping("/getShippedOrders")
    public ResponseEntity getShippedOrders()
    {
        return shopKeeperOrderService.findAllByState("Shipped");
    }

    @GetMapping("/getDeliveredOrders")
    public ResponseEntity<List<Shop_keeper_orders>> getDeliveredOrders(@RequestParam("page_num") int page_num)
    {
        try
        {
            return new ResponseEntity<>(shopKeeperOrderService.findDeliveredOrders(page_num).getContent(), HttpStatus.OK);
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
            return new ResponseEntity<>(shopKeeperOrderService.findCancelledOrders(page_num).getContent(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PutMapping("/setOrderState")
    public ResponseEntity setOrderState(@RequestParam(name="order_id") long order_id, @RequestParam("order_state") String order_state)
    {
        return shopKeeperOrderService.setOrderState(order_id, order_state);
    }

    @PostMapping("/shop_keeper_order")
    public ResponseEntity add_shop_order(@RequestBody Shop_keeper_orders shop_keeper_orders, @RequestParam(name = "mobile_number") String mobile_number)
    {
        return shopKeeperOrderService.addOrder(shop_keeper_orders, mobile_number);
    }

    @GetMapping("/get_shop_keeper_order")
    public List<Shop_keeper_orders> getShopKeeperOrders(@RequestParam(name = "page") int page, @RequestParam(name = "mobile_number") String mobile_number)
    {
        Page<Shop_keeper_orders> pagedList = shopKeeperOrderService.getAllProducts(page, mobile_number);
        return pagedList.getContent();
    }
}
