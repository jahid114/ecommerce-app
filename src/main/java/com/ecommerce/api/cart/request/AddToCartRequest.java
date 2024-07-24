package com.ecommerce.api.cart.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AddToCartRequest implements Serializable {
    private Long userId;
    private int itemQuantity;
    private Long cartId;
    private Long productId;
    private Long cartItemId;
}
