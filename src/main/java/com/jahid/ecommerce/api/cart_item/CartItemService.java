package com.jahid.ecommerce.api.cart_item;

import com.jahid.ecommerce.api.cart.Cart;
import com.jahid.ecommerce.api.cart.CartRepository;
import com.jahid.ecommerce.api.product.Product;
import com.jahid.ecommerce.api.product.ProductRepository;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    CartItemService(@Autowired CartItemRepository cartItemRepository, @Autowired ModelMapper modelMapper, @Autowired ProductRepository productRepository,
                    CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public ResponseCartItemDto createCartItem(CreateCartItemDto createCartItemDto) {
        Product product = productRepository.findById(createCartItemDto.getProductId()).orElseThrow(()->new NotFoundException(createCartItemDto.getProductId(),Product.class.getName()));
        Cart cart = cartRepository.findById(createCartItemDto.getCartId()).orElseThrow(()->new NotFoundException(createCartItemDto.getCartId(),Cart.class.getSimpleName()));
        CartItem cartItem = this.modelMapper.map(createCartItemDto, CartItem.class);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(((long) product.getPrice() * cartItem.getItemQuantity()));
//        cart.getCartItemSet().add(cartItem);
//        product.getCartItemSet().add(cartItem);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        CartItem createdItem = cartItemRepository.save(cartItem);
        return modelMapper.map(createdItem, ResponseCartItemDto.class);
    }
}
