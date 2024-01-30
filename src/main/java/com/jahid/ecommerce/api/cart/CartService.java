package com.jahid.ecommerce.api.cart;

import com.jahid.ecommerce.api.cart_item.CartItemService;
import com.jahid.ecommerce.api.cart_item.RequestCartItemDto;
import com.jahid.ecommerce.api.user.User;
import com.jahid.ecommerce.api.user.UserRepository;
import com.jahid.ecommerce.api.utility.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private final CartItemService cartItemService;

    private final ModelMapper modelMapper;

    public Cart createCart(Long userId){
        System.out.println(userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId,
                        User.class.getSimpleName()));
        Cart cart = new Cart();
        cart.setTotalPrice(0L);
        cart.setTotalQuantity(0);
        user.setCart(cart);
        cart.setUser(user);
        return cart;
    }

    public CartResponse addToCart(RequestCartItemDto requestCartItemDto, Long userId){
        Cart cart;
        if(requestCartItemDto.getCartId() == null){
            cart = createCart(userId);
            requestCartItemDto.setCartId(cart.getCartId());
        }
        else {
            cart = cartRepository.findById(requestCartItemDto.getCartId())
                    .orElseThrow(()-> new NotFoundException(
                            requestCartItemDto.getCartId(), Cart.class.getSimpleName()));
        }
        return cartItemService.createCartItem(requestCartItemDto, cart);
    }

    public List<CartResponse> getAllCart(){
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cart -> modelMapper.map(cart, CartResponse.class))
                .collect(Collectors.toList());
    }

    public CartResponse getCartByID(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException(cartId,Cart.class.getSimpleName()));
        return modelMapper.map(cart, CartResponse.class);
    }

    public void deleteCart(Long cartId){
        cartRepository.findById(cartId).orElseThrow(()-> new NotFoundException(cartId,Cart.class.getSimpleName()));
        cartRepository.deleteById(cartId);
    }

}
