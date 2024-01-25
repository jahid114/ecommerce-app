package com.jahid.ecommerce.api.cart_item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link CartItem}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCartItemDto implements Serializable {
    private Long cartItemId;
    private int unitPrice;
    private Long totalPrice;
    private int itemQuantity;
    private CartDto cart;
    private ProductDto product;

    /**
     * DTO for {@link com.jahid.ecommerce.api.cart.Cart}
     */
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CartDto implements Serializable {
        private Long cartId;
    }

    /**
     * DTO for {@link com.jahid.ecommerce.api.product.Product}
     */
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductDto implements Serializable {
        private Long productId;
    }
}