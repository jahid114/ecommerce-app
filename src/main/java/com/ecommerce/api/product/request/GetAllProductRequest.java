package com.ecommerce.api.product.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllProductRequest{
    private Integer lowPrice;
    private Integer highPrice;
    private String sku;
    private String productName;
}