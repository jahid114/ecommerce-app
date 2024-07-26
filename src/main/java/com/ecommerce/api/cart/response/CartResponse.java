package com.ecommerce.api.cart.response;


import com.ecommerce.api.cart.model.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * DTO for {@link Cart}
 */
@NoArgsConstructor
@Getter
@Setter
public class CartResponse implements Serializable {
    private Long cartId;
    private Long totalPrice;
    private int totalQuantity;
    private UserDto user;
    private List<ResponseCartItemDto> cartItems;


    @NoArgsConstructor
    @Getter
    @Setter
    private static class UserDto implements Serializable{
        private Long userId;
        private String name;
        private String mobileNo;
        private boolean isActive;
        private String email;
        private String address;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    private static class ResponseCartItemDto implements Serializable {
        private Long cartItemId;
        private int unitPrice;
        private Long totalPrice;
        private int itemQuantity;
        private CartItemResponse.ProductDto product;
    }
}