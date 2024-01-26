package com.jahid.ecommerce.api.cart_item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.jahid.ecommerce.api.cart_item.CartItem}
 */
@NoArgsConstructor
@Getter
@Setter
public class RequestCartItemDto implements Serializable {
    private int itemQuantity;
    private Long cartId;
    private Long productId;
    private Long cartItemId;
}