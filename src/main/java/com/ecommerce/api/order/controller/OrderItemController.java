package com.ecommerce.api.order.controller;

import com.ecommerce.api.order.service.OrderItemService;
import com.ecommerce.api.order.request.OrderItemRequest;
import com.ecommerce.api.order.response.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/orderItems" )
@RequiredArgsConstructor( onConstructor_ = @Autowired)
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PatchMapping
    public ResponseEntity<OrderItemResponse> updateOrderItem(@RequestBody OrderItemRequest orderItemRequest) {
        OrderItemResponse responseDto = orderItemService
                .updateOrderItem(orderItemRequest);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable String orderItemId){
        orderItemService.deleteOrderItem(Long.parseLong(orderItemId));
        return ResponseEntity.noContent().build();
    }
}