package com.jahid.ecommerce.api.order_item;

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
    public ResponseEntity<ResponseOrderItemDto> updateOrderItem(@RequestBody RequestOrderItemDto requestOrderItemDto) {
        ResponseOrderItemDto responseDto = orderItemService
                .updateOrderItem(requestOrderItemDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable String orderItemId){
        orderItemService.deleteOrderItem(Long.parseLong(orderItemId));
        return ResponseEntity.noContent().build();
    }
}
