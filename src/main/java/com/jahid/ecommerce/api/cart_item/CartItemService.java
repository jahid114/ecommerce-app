package com.jahid.ecommerce.api.cart_item;

import com.jahid.ecommerce.api.cart.Cart;
import com.jahid.ecommerce.api.cart.CartRepository;
import com.jahid.ecommerce.api.cart.CartResponseDto;
import com.jahid.ecommerce.api.product.Product;
import com.jahid.ecommerce.api.product.ProductRepository;
import com.jahid.ecommerce.api.utility.NotFoundException;
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

    public CartResponseDto createCartItem(RequestCartItemDto requestCartItemDto, Cart cart) {
        Product product = productRepository.findById(requestCartItemDto.getProductId()).orElseThrow(()->new NotFoundException(requestCartItemDto.getProductId(),Product.class.getName()));
        CartItem cartItem = this.modelMapper.map(requestCartItemDto, CartItem.class);
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
        return modelMapper.map(cartRepository.save(cart), CartResponseDto.class);
    }
    // Todo fix quantity bug
    public ResponseCartItemDto updateCartItem(Long Id,int quantity){
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
        return this.modelMapper.map(updatedCartItem, ResponseCartItemDto.class);
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
