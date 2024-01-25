package com.jahid.ecommerce.api.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Cart}
 */
@NoArgsConstructor
@Getter
@Setter
public class CartResponseDto implements Serializable {
    private Long cartId;
    private Long totalPrice;
    private int totalQuantity;
    private UserDto user;

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
}