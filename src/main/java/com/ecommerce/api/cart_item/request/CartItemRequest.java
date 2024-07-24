package com.ecommerce.api.cart_item.request;

import com.ecommerce.api.cart_item.model.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link CartItem}
 */
@NoArgsConstructor
@Getter
@Setter
public class CartItemRequest implements Serializable {
    private int itemQuantity;
    private Long cartId;
    private Long productId;
    private Long cartItemId;
}