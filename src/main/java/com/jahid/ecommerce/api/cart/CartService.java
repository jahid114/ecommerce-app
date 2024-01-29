package com.jahid.ecommerce.api.cart;

import com.jahid.ecommerce.api.cart_item.CartItemService;
import com.jahid.ecommerce.api.cart_item.RequestCartItemDto;
import com.jahid.ecommerce.api.user.User;
import com.jahid.ecommerce.api.user.UserRepository;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;


    CartService(@Autowired CartRepository cartRepository, CartItemService cartItemService, @Autowired ModelMapper modelMapper, @Autowired UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Cart createCart(Long userId){
        System.out.println(userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId, User.class.getSimpleName()));
        Cart cart = new Cart();
        cart.setTotalPrice(0L);
        cart.setTotalQuantity(0);
        user.setCart(cart);
        cart.setUser(user);
        return cart;
    }

    public CartResponseDto addToCart(RequestCartItemDto requestCartItemDto, Long userId){
        Cart cart;
        if(requestCartItemDto.getCartId() == null){
            cart = createCart(userId);
            requestCartItemDto.setCartId(cart.getCartId());
        }else {
            cart = cartRepository.findById(requestCartItemDto.getCartId())
                    .orElseThrow(()-> new NotFoundException(requestCartItemDto.getCartId(),
                            Cart.class.getSimpleName()));
        }
        return cartItemService.createCartItem(requestCartItemDto, cart);
    }
    public List<CartResponseDto> getAllCart(){
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> modelMapper.map(cart, CartResponseDto.class)).collect(Collectors.toList());
    }

    public CartResponseDto getCartByID(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException(cartId,Cart.class.getSimpleName()));
        return modelMapper.map(cart, CartResponseDto.class);
    }

    public void deleteCart(Long cartId){
        cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException(cartId,Cart.class.getSimpleName()));
        cartRepository.deleteById(cartId);
    }

}
