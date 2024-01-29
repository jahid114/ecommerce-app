package com.jahid.ecommerce.api.order;

import com.jahid.ecommerce.api.order_item.RequestOrderItemDto;
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
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderResponseDto responseDto = orderService.placeOrder(orderRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/addToOrder")
    public ResponseEntity<OrderResponseDto> addToCart(@RequestBody RequestOrderItemDto requestOrderItemDto){
        OrderResponseDto responseDto = orderService.addToOrder(requestOrderItemDto);
        return ResponseEntity.ok(responseDto);
    }

    // admin only
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(){
        List<OrderResponseDto> responseDto= orderService.getAllOrder();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderByID(@PathVariable String orderId){
        OrderResponseDto responseDto = orderService.getOrderById(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/approve")
    public ResponseEntity<OrderResponseDto> approveOrder(@PathVariable String orderId){
        OrderResponseDto responseDto = orderService.approveOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/readyToDeliver")
    public ResponseEntity<OrderResponseDto> OrderReadyToDeliver(@PathVariable String orderId){
        OrderResponseDto responseDto = orderService.updateReadyToDeliver(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable String orderId){
        OrderResponseDto responseDto = orderService.cancelOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<OrderResponseDto> updateOrderDelivery(@PathVariable String orderId){
        OrderResponseDto responseDto = orderService.updateDeliveryDateOfOrder(Long.parseLong(orderId));
        return ResponseEntity.ok(responseDto);
    }

    // for admin only
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(Long.parseLong(orderId));
        return ResponseEntity.noContent().build();
    }

}
