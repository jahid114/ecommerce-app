package com.jahid.ecommerce.api.cart;

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
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;


    CartService(@Autowired CartRepository cartRepository, @Autowired ModelMapper modelMapper, @Autowired UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public CartResponseDto createCart(CreateCartDto createCartDto){
        User user = this.userRepository.findById(createCartDto.getUserId()).orElseThrow(() -> new NotFoundException(createCartDto.getUserId(), User.class.getSimpleName()));
        Cart cart = this.modelMapper.map(createCartDto, Cart.class);
        cart.setTotalPrice(0L);
        user.setCart(cart);
        cart.setUser(user);
        cart.setTotalPrice(0L);
        Cart createdCart = this.cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return this.modelMapper.map(createdCart, CartResponseDto.class);
    }

    public List<CartResponseDto> getAllCart(){
        List<Cart> carts = cartRepository.findAll();
        List<CartResponseDto> cartResponseDtos = carts.stream().map(cart -> modelMapper.map(cart, CartResponseDto.class)).collect(Collectors.toList());
        return cartResponseDtos;
    }
}
