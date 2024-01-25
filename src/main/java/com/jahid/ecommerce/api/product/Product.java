package com.jahid.ecommerce.api.product;

import com.jahid.ecommerce.api.order_item.OrderItem;
import com.jahid.ecommerce.api.utility.EnumConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


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
    private Long productId;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Lob
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

    @Column(name = "product_category",columnDefinition = ("varchar(255) default 'UNKNOWN'"),nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumConstants.Category productCategory = EnumConstants.Category.UNKNOWN;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItemSet;
}
