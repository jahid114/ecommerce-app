package com.ecommerce.api.cart.response;

import com.ecommerce.api.product.response.ProductResponse;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemResponse {
    private Long id;
    private Long totalPrice;
    private Integer unitPrice;
    private Integer itemQuantity;
    private ProductResponse product;
}