package com.jahid.ecommerce.api.cart_item;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    CartItemService(@Autowired CartItemRepository cartItemRepository, @Autowired ModelMapper modelMapper){
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }
}
