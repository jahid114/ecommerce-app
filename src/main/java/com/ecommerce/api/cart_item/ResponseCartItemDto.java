package com.ecommerce.api.cart_item;

import com.ecommerce.api.cart.Cart;
import com.ecommerce.api.product.Product;
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
     * DTO for {@link Cart}
     */
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CartDto implements Serializable {
        private Long cartId;
    }

    /**
     * DTO for {@link Product}
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDto implements Serializable {
        private Long productId;
    }
}