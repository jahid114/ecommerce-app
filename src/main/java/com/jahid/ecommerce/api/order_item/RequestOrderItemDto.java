package com.jahid.ecommerce.api.order_item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link OrderItem}
 */
@NoArgsConstructor
@Getter
@Setter
public class RequestOrderItemDto implements Serializable {
    private Long orderItemId;
    private int itemQuantity;
    private Long productId;
    private Long orderId;
    private Long cartItemId;
}