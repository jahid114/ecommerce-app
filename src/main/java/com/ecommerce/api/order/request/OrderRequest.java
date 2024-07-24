package com.ecommerce.api.order.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequest implements Serializable {
    private Long cartId;
}
