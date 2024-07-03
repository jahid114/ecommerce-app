package com.ecommerce.api.cart;

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
public class CreateCartDto implements Serializable {
    private Long userId;
}