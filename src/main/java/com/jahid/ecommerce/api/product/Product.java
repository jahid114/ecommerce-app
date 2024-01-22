package com.jahid.ecommerce.api.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum Category{
    UNKNOWN,
    SMARTPHONE,
    ELECTRONICS,
    MOBILE_ACCESSORIES,
    COMPUTER_ACCESSORIES
}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long product_id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_details")
    private String productDetails;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @Column(name = "price",nullable = false)
    private int price;

    @Column(name = "in_stock",nullable = false)
    private int inStock;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "product_category",columnDefinition = ("varchar(255) default 'UNKNOWN'"))
    @Enumerated(EnumType.STRING)
    private Category productCategory;
}
