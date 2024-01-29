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
public class ProductDto implements Serializable {
    private Long productId;
    private String productName;
    private String productDetails;
    private int price;
    private int inStock;
    private String sku;
    private EnumConstants.Category productCategory;
    private String productImageUrl;
    private String productImagePath;


    @Override
    public String toString() {
        return "Name: "+productName+", details: "+productDetails+", price: "+price+", in stock: " + inStock+ ", sku: "+sku+", category: "+productCategory;
    }
}