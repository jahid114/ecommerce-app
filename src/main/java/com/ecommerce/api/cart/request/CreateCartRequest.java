package com.ecommerce.api.cart.request;

import com.ecommerce.api.cart.model.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Cart}
 */
@NoArgsConstructor
@Getter
@Setter
public class CreateCartRequest implements Serializable {
    private Long userId;
}