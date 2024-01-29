package com.jahid.ecommerce.api.order_item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PatchMapping
    public ResponseEntity<ResponseOrderItemDto> updateOrderItem(@RequestBody RequestOrderItemDto requestOrderItemDto) {
        ResponseOrderItemDto responseDto = orderItemService.updateOrderItem(requestOrderItemDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable String orderItemId){
        orderItemService.deleteOrderItem(Long.parseLong(orderItemId));
        return ResponseEntity.noContent().build();
    }
}
