package com.jahid.ecommerce.api.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.jahid.ecommerce.api.cart.Cart}
 */
@NoArgsConstructor
@Getter
@Setter
public class CreateCartDto implements Serializable {
    private Long userId;
}