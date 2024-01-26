package com.jahid.ecommerce.api.cart;

import com.jahid.ecommerce.api.cart_item.RequestCartItemDto;
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
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody AddToCartRequestDto addToCartRequestDto){
        System.out.println(addToCartRequestDto.getUserId());
        RequestCartItemDto requestCartItemDto = modelMapper.map(addToCartRequestDto, RequestCartItemDto.class);
        CartResponseDto response = cartService.addToCart(requestCartItemDto,addToCartRequestDto.getUserId());
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getAllCart(){
        List<CartResponseDto> cartResponseDto = cartService.getAllCart();
        return ResponseEntity.ok(cartResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable String id){
        CartResponseDto cart = cartService.getCartByID(Long.parseLong(id));
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id){
        cartService.deleteCart(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
