package com.ecommerce.api.order_item.request;

import com.ecommerce.api.order_item.model.OrderItem;
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
public class OrderItemRequest implements Serializable {
    private Long orderItemId;
    private int itemQuantity;
    private Long productId;
    private Long orderId;
}