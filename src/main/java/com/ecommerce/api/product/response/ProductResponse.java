package com.ecommerce.api.product.response;

import com.ecommerce.api.utility.EnumConstants;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponse {
    private Long id;
    private Integer price;
    private Integer inStock;
    private String sku;
    private String productName;
    private String productDetails;
    private String productImageUrl;
    private String productImagePath;
    private EnumConstants.Category productCategory;
}