package com.ecommerce.api.order_item;

import com.ecommerce.api.order.Order;
import com.ecommerce.api.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link OrderItem}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseOrderItemDto implements Serializable {
    private Long orderItemId;
    private int unitPrice;
    private Long totalPrice;
    private int itemQuantity;
    private ProductDto product;
    private OrderDto order;

    /**
     * DTO for {@link Product}
     */
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class ProductDto implements Serializable {
        private Long productId;
    }

    /**
     * DTO for {@link Order}
     */
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class OrderDto implements Serializable {
        private Long orderId;
    }
}