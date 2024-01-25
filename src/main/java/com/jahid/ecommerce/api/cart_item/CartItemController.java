package com.jahid.ecommerce.api.cart_item;

import com.jahid.ecommerce.api.cart.CreateCartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public CreateCartDto createCartItem(CreateCartDto createCartDto){
        return createCartDto;
    }
}
