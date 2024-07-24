package com.ecommerce.api.product.response;

import com.ecommerce.api.product.model.Product;
import com.ecommerce.api.utility.EnumConstants;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponse implements Serializable {
    private Long productId;
    private String productName;
    private String productDetails;
    private int price;
    private int inStock;
    private String sku;
    private EnumConstants.Category productCategory;
    private String productImageUrl;
    private String productImagePath;
}