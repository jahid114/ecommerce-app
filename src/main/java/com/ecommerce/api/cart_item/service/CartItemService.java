package com.ecommerce.api.cart_item.service;

import com.ecommerce.api.cart.model.Cart;
import com.ecommerce.api.cart.service.CartRepository;
import com.ecommerce.api.cart.response.CartResponse;
import com.ecommerce.api.cart_item.model.CartItem;
import com.ecommerce.api.cart_item.request.CartItemRequest;
import com.ecommerce.api.cart_item.response.CartItemResponse;
import com.ecommerce.api.cart_item.service.CartItemRepository;
import com.ecommerce.api.product.Product;
import com.ecommerce.api.product.ProductRepository;
import com.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public CartResponse createCartItem(CartItemRequest cartItemRequest, Cart cart) {
        Product product = productRepository.findById(cartItemRequest.getProductId()).orElseThrow(()->new NotFoundException(cartItemRequest.getProductId(),Product.class.getName()));
        CartItem cartItem = this.modelMapper.map(cartItemRequest, CartItem.class);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice(((long) product.getPrice() * cartItem.getItemQuantity()));
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        if(cart.getCartItems() == null){
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);
        }else {
            cart.getCartItems().add(cartItem);
        }
        cart.setTotalQuantity(cart.getTotalQuantity()+cartItem.getItemQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+cartItem.getTotalPrice());
        return modelMapper.map(cartRepository.save(cart), CartResponse.class);
    }
    // Todo fix quantity bug
    public CartItemResponse updateCartItem(Long Id, int quantity){
        CartItem cartItem = cartItemRepository.findById(Id).orElseThrow(()->new NotFoundException(Id,CartItem.class.getSimpleName()));
        Cart cart = cartRepository.findById(cartItem.getCart().getCartId()).orElseThrow(()->new NotFoundException(Id,Cart.class.getSimpleName()));
        if(quantity>0){
            cart.setTotalPrice(cart.getTotalPrice()-cartItem.getTotalPrice());
            cart.setTotalQuantity(cart.getTotalQuantity()-cartItem.getItemQuantity());
            cartItem.setItemQuantity(quantity);
            cartItem.setTotalPrice(((long) quantity *cartItem.getUnitPrice()));
            cart.setTotalQuantity(cart.getTotalQuantity()+quantity);
            cart.setTotalPrice(cart.getTotalPrice()+cartItem.getTotalPrice());
        }
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        return this.modelMapper.map(updatedCartItem, CartItemResponse.class);
    }

    public void deleteCartItem(Long Id){
        CartItem cartItem = cartItemRepository.findById(Id).orElseThrow(()->new NotFoundException(Id,CartItem.class.getSimpleName()));
        Cart cart = cartRepository.findById(cartItem.getCart().getCartId()).orElseThrow(()->new NotFoundException(Id,Cart.class.getSimpleName()));
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getTotalPrice());
        cart.setTotalQuantity(cart.getTotalQuantity()-cartItem.getItemQuantity());
        cartItemRepository.deleteById(Id);
        cartRepository.save(cart);
    }
}
