package com.jahid.ecommerce.api.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link Order}
 */
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto implements Serializable {
    private Long order_id;
    private Long totalPrice;
    private int totalQuantity;
    private List<OrderItemDto> orderItems;
    private UserDto user;
    private OrderStatusDto orderStatus;

    /**
     * DTO for {@link com.jahid.ecommerce.api.order_item.OrderItem}
     */
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderItemDto implements Serializable {
        private Long orderItemId;
        private int unitPrice;
        private Long totalPrice;
        private int itemQuantity;
        private ProductDto product;

        /**
         * DTO for {@link com.jahid.ecommerce.api.product.Product}
         */
        @NoArgsConstructor
        @Getter
        @Setter
        public static class ProductDto implements Serializable {
            private Long productId;
        }
    }

    /**
     * DTO for {@link com.jahid.ecommerce.api.user.User}
     */
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserDto implements Serializable {
        private Long userId;
        private String name;
    }

    /**
     * DTO for {@link com.jahid.ecommerce.api.order_status.OrderStatus}
     */
    @NoArgsConstructor
    @Setter
    @Getter
    public static class OrderStatusDto implements Serializable {
        private Long order_status_id;
        private Date orderPlaceDateTime;
        private Date orderApproveDateTime;
        private Date readyToDeliverDateTime;
        private Date deliveryDateTime;
    }
}