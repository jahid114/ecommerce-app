package com.ecommerce.api.order.controller;

import com.ecommerce.api.order.request.OrderRequest;
import com.ecommerce.api.order.response.OrderResponse;
import com.ecommerce.api.order.service.OrderService;
import com.ecommerce.api.order_item.request.OrderItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse responseDto = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/addToOrder")
    public ResponseEntity<OrderResponse> addToCart(@RequestBody OrderItemRequest orderItemRequest){
        OrderResponse responseDto = orderService.addToOrder(orderItemRequest);
        return ResponseEntity.ok(responseDto);
    }

    // admin only
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        List<OrderResponse> responseDto= orderService.getAllOrder();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByID(@PathVariable String orderId){
        OrderResponse responseDto = orderService.getOrderById(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/approve")
    public ResponseEntity<OrderResponse> approveOrder(@PathVariable String orderId){
        OrderResponse responseDto = orderService.approveOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/readyToDeliver")
    public ResponseEntity<OrderResponse> OrderReadyToDeliver(@PathVariable String orderId){
        OrderResponse responseDto = orderService.updateReadyToDeliver(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId){
        OrderResponse responseDto = orderService.cancelOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<OrderResponse> updateOrderDelivery(@PathVariable String orderId){
        OrderResponse responseDto = orderService.updateDeliveryDateOfOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    // for admin only
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(Long.parseLong(orderId));
        return ResponseEntity.noContent().build();
    }

}
