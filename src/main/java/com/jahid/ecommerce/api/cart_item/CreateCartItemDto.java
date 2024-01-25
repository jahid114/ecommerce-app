package com.jahid.ecommerce.api.cart_item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.jahid.ecommerce.api.order_item.CartItem}
 */
@NoArgsConstructor
@Getter
@Setter
public class CreateCartItemDto implements Serializable {
    private int itemQuantity;
    private Long cartId;
    private Long productId;
}