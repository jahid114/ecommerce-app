package com.jahid.ecommerce.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    CartController(@Autowired CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponseDto> createCart(@RequestBody CreateCartDto createCartDto){
        CartResponseDto createdCart = this.cartService.createCart(createCartDto);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getAllCart(){
        List<CartResponseDto> cartResponseDtos = cartService.getAllCart();
        return ResponseEntity.ok(cartResponseDtos);
    }
}
