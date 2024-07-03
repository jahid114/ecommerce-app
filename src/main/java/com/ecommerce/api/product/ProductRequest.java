package com.ecommerce.api.product;

import com.ecommerce.api.utility.EnumConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest implements Serializable {
    @NotBlank(message = "Product should have a name")
    private String productName;
    private String productDetails;

    @Min(value = 0, message = "Price shouldn't be negetive")
    private int price;
    private int inStock;
    private String sku;
    private EnumConstants.Category productCategory;
}