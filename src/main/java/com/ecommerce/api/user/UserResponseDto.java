package com.ecommerce.api.user;

import com.ecommerce.api.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponseDto implements Serializable {
    private Long userId;
    private String name;
    private String mobileNo;
    private boolean isActive;
    private String email;
    private String address;
    private CartDto cart;

    /**
     * DTO for {@link Cart}
     */
    @NoArgsConstructor
    @Setter
    @Getter
    private static class CartDto implements Serializable {
        private Long cartId;
        private Long totalPrice;
        private int totalQuantity;
    }
}