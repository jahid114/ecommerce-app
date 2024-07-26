package com.ecommerce.api.product.request;

import com.ecommerce.api.utility.EnumConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest{

    @Min(value = 0, message = "Price shouldn't be negetive")
    private Integer price;
    private Integer inStock;

    @NotBlank(message = "Product should have a name")
    private String productName;
    private String productDetails;
    private String sku;
    private EnumConstants.Category productCategory;
}