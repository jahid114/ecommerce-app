package com.ecommerce.api.cart_item.response;

import com.ecommerce.api.cart.model.Cart;
import com.ecommerce.api.cart_item.model.CartItem;
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
public class CartItemResponse implements Serializable {
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