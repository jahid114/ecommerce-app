package com.ecommerce.api.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto implements Serializable {
    private Long cartId;
}
