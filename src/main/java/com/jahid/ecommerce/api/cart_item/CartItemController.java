package com.jahid.ecommerce.api.cart_item;

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
    public ResponseEntity<ResponseCartItemDto> updateCartItem(@PathVariable String cartItemId, @RequestBody RequestCartItemDto requestCartItemDto){
        ResponseCartItemDto response = cartItemService.updateCartItem(Long.parseLong(cartItemId),requestCartItemDto.getItemQuantity());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable String cartItemId){
        cartItemService.deleteCartItem(Long.parseLong(cartItemId));
        return ResponseEntity.noContent().build();
    }
}
