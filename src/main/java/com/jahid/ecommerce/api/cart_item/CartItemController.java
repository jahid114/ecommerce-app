package com.jahid.ecommerce.api.cart_item;

import com.jahid.ecommerce.api.cart.CreateCartDto;
import com.jahid.ecommerce.api.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    CartItemController(@Autowired CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<ResponseCartItemDto> createCartItem(@RequestBody CreateCartItemDto createCartItemDto){
        ResponseCartItemDto cartItem = cartItemService.createCartItem(createCartItemDto);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
}
