package com.ecommerce.api.cart.controller;

import com.ecommerce.api.cart.request.AddToCartRequest;
import com.ecommerce.api.cart.response.CartResponse;
import com.ecommerce.api.cart.service.CartService;
import com.ecommerce.api.cart_item.RequestCartItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ModelMapper modelMapper;

    CartController(@Autowired CartService cartService, ModelMapper modelMapper){
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCartRequest addToCartRequest){
        System.out.println(addToCartRequest.getUserId());
        RequestCartItemDto requestCartItemDto = modelMapper.map(addToCartRequest, RequestCartItemDto.class);
        CartResponse response = cartService.addToCart(requestCartItemDto, addToCartRequest.getUserId());
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCart(){
        List<CartResponse> cartResponse = cartService.getAllCart();
        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable String id){
        CartResponse cart = cartService.getCartByID(Long.parseLong(id));
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id){
        cartService.deleteCart(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
