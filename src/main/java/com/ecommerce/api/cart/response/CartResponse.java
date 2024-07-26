package com.ecommerce.api.cart.response;


import com.ecommerce.api.user.response.UserResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class CartResponse{
    private Long id;
    private Long totalPrice;
    private Integer totalQuantity;
    private UserResponse user;
    private List<CartItemResponse> cartItems;
}