package com.ecommerce.api.cart_item.controller;

import com.ecommerce.api.cart_item.service.CartItemService;
import com.ecommerce.api.cart_item.request.CartItemRequest;
import com.ecommerce.api.cart_item.response.CartItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    CartItemController(@Autowired CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }


    @PatchMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(@PathVariable String cartItemId, @RequestBody CartItemRequest cartItemRequest){
        CartItemResponse response = cartItemService.updateCartItem(Long.parseLong(cartItemId), cartItemRequest.getItemQuantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable String cartItemId){
        cartItemService.deleteCartItem(Long.parseLong(cartItemId));
        return ResponseEntity.noContent().build();
    }
}
