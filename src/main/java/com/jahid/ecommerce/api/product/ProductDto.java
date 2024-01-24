package com.jahid.ecommerce.api.product;

import com.jahid.ecommerce.api.utility.EnumConstants;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.jahid.ecommerce.api.product.Product}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long product_id;
    private String productName;
    private String productDetails;
    private int price;
    private int inStock;
    private String sku;
    private EnumConstants.Category productCategory;
}